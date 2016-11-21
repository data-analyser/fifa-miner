package com.fifaminer.service.price.impl;

import com.fifaminer.entity.PriceHistory;
import com.fifaminer.service.price.*;
import com.fifaminer.service.price.model.PlayerPrice;
import com.fifaminer.service.price.policy.PriceActualityPolicy;
import com.fifaminer.service.price.policy.SellStartPriceDefinitionPolicy;
import com.fifaminer.service.price.policy.MaxBuyPriceDefinitionPolicy;
import com.fifaminer.service.price.policy.SellBuyNowPriceDefinitionPolicy;
import com.fifaminer.service.price.model.PriceStatistics;
import com.fifaminer.service.setting.SettingsService;
import com.fifaminer.timeseries.TimeSeriesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.fifaminer.client.dto.strategy.PriceStrategy.*;
import static java.lang.Long.compare;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.MapUtils.isEmpty;
import static org.apache.commons.collections4.MapUtils.isNotEmpty;
import static org.apache.commons.lang3.math.NumberUtils.*;

@Slf4j
@Service
public class PriceServiceIml implements PriceService {

    private final PriceHistoryService priceHistoryService;
    private final PriceStatisticsService priceStatisticsService;
    private final TimeSeriesService timeSeriesService;
    private final MaxBuyPriceDefinitionPolicy maxBuyPolicy;
    private final SellStartPriceDefinitionPolicy sellStartPolicy;
    private final SellBuyNowPriceDefinitionPolicy sellBuyNowPolicy;
    private final PriceActualityPolicy priceActualityPolicy;
    private final TaxService taxService;
    private final SettingsService settingsService;

    @Autowired
    public PriceServiceIml(PriceHistoryService priceHistoryService,
                           PriceStatisticsService priceStatisticsService,
                           TimeSeriesService timeSeriesService,
                           MaxBuyPriceDefinitionPolicy maxBuyPolicy,
                           SellStartPriceDefinitionPolicy sellStartPolicy,
                           SellBuyNowPriceDefinitionPolicy sellBuyNowPolicy,
                           PriceActualityPolicy priceActualityPolicy,
                           TaxService taxService,
                           SettingsService settingsService) {
        this.priceHistoryService = priceHistoryService;
        this.priceStatisticsService = priceStatisticsService;
        this.timeSeriesService = timeSeriesService;
        this.maxBuyPolicy = maxBuyPolicy;
        this.sellStartPolicy = sellStartPolicy;
        this.sellBuyNowPolicy = sellBuyNowPolicy;
        this.priceActualityPolicy = priceActualityPolicy;
        this.taxService = taxService;
        this.settingsService = settingsService;
    }

    @Override
    public Integer getMaxBuyPrice(Long playerId) {
        log.info("Get max buy price for player = {}", playerId);
        PriceHistory priceHistory = priceHistoryService.findByPlayerId(playerId);

        if (isNull(priceHistory) || isEmpty(priceHistory.getHistory())) {
            log.info("Cannot calculate max buy price, prices history is empty for player = {}", playerId);
            return INTEGER_ZERO;
        }

        List<PriceStatistics> priceStatistics = calculateStatistics(
                priceHistory.getHistory()
        );

        Double forecastedMin = timeSeriesService.forecast(
                extractProperty(priceStatistics, value -> value.getMin().doubleValue())
        );

        log.info("Forecasted min price = {}, for player id = {}", forecastedMin, playerId);

        return maxBuyPolicy.define(priceStatistics, forecastedMin);
    }

    @Override
    public Integer getSellStartPrice(Long playerId) {
        log.info("Get sell start price for player id = {}", playerId);
        return sellStartPolicy.define(getSellBuyNowPrice(playerId));
    }

    @Override
    public Integer getSellBuyNowPrice(Long playerId) {
        PriceHistory priceHistory = priceHistoryService.findByPlayerId(playerId);

        if (isNull(priceHistory) || isEmpty(priceHistory.getHistory())) {
            log.info("Cannot calculate sell buy now price, prices history is empty for player = {}", playerId);
            return INTEGER_ZERO;
        }

        List<PriceStatistics> priceStatistics = calculateStatistics(
                priceHistory.getHistory()
        );

        Double forecastedMedian = timeSeriesService.forecast(
                extractProperty(priceStatistics, value -> value.getMedian().doubleValue())
        );
        log.info("Forecasted median  = {} for player id = {}", forecastedMedian, playerId);
        Double forecastedMin = timeSeriesService.forecast(
                extractProperty(priceStatistics, value -> value.getMin().doubleValue())
        );
        log.info("Forecasted min  = {} for player id = {}", forecastedMin, playerId);
        return sellBuyNowPolicy.define(
                forecastedMin,
                forecastedMedian,
                getLastPriceDistribution(priceHistory.getHistory())
        );
    }

    private Map<Integer, Integer> getLastPriceDistribution(Map<Long, Map<Integer, Integer>> history) {
        Long lastPricesWithData = history.entrySet().stream()
                .filter(entry -> isNotEmpty(entry.getValue()))
                .map(Entry::getKey)
                .sorted(byTimeStamp().reversed())
                .findFirst()
                .orElse(LONG_ZERO);

        return history.get(lastPricesWithData);
    }

    @Override
    public Integer getBuyNowProfit(Long playerId) {
        log.info("Get buy now profit for player id = {}", playerId);
        Integer profit = taxService.reduceTax(getSellBuyNowPrice(playerId)) - getMaxBuyPrice(playerId);
        log.info("Buy now profit  = {} for player id = {}", profit, playerId);
        return profit;
    }

    @Override
    public Integer getStartProfit(Long playerId) {
        log.info("Get start sell profit for player id = {}", playerId);
        Integer profit = taxService.reduceTax(getSellStartPrice(playerId)) - getMaxBuyPrice(playerId);
        log.info("Start sell profit  = {} for player id = {}", profit, playerId);
        return profit;
    }

    @Override
    public PlayerPrice getPricesSummary(Long playerId) {
        log.info("Get prices summary for player id = {}", playerId);

        Integer maxBuyNowPrice = getMaxBuyPrice(playerId);
        Integer sellBuyNowPrice = getSellBuyNowPrice(playerId);
        Integer sellStartPrice = sellStartPolicy.define(sellBuyNowPrice);
        Integer startProfit = taxService.reduceTax(sellStartPrice) - maxBuyNowPrice;
        Integer buyNowProfit = taxService.reduceTax(sellBuyNowPrice) - maxBuyNowPrice;

        PlayerPrice playerPrice = new PlayerPrice(
                playerId, maxBuyNowPrice, sellStartPrice,
                sellBuyNowPrice, startProfit, buyNowProfit,
                settingsService.getSetting(MAX_BUY_PRICE.name()),
                settingsService.getSetting(SELL_START_PRICE.name()),
                settingsService.getSetting(SELL_BUY_NOW_PRICE.name())
        );

        log.info("Calculated prices summary = {}", playerPrice);

        return playerPrice;
    }

    @Override
    public boolean isPriceDistributionActual(Long playerId) {
        log.info("Check is actual prices distribution for player id = {}", playerId);
        boolean isActual = priceActualityPolicy.isActualPrices(getLastDistributionTime(playerId));
        log.info("Prices distribution is actual = {}, player id = {}", isActual, playerId);
        return isActual;
    }

    private Long getLastDistributionTime(Long playerId) {
        PriceHistory priceHistory = priceHistoryService.findByPlayerId(playerId);

        if (isNull(priceHistory) || isEmpty(priceHistory.getHistory())) return LONG_ZERO;

        return priceHistory.getHistory().entrySet()
                .stream()
                .filter(entry -> isNotEmpty(entry.getValue()))
                .map(Entry::getKey)
                .sorted(byTimeStamp().reversed())
                .findFirst()
                .orElse(LONG_ZERO);
    }

    private List<Double> extractProperty(List<PriceStatistics> priceStatistics,
                                         Function<PriceStatistics, Double> extractFunction) {
        return priceStatistics.stream()
                .map(extractFunction)
                .collect(toList());
    }

    private List<PriceStatistics> calculateStatistics(Map<Long, Map<Integer, Integer>> history) {
        return history.entrySet()
                .stream()
                .map(entry -> priceStatisticsService.calculatePriceStatistics(entry.getKey(), entry.getValue()))
                .sorted((current, next) -> compare(current.getTimestamp(), next.getTimestamp()))
                .filter(withoutZeroStats())
                .collect(toList());
    }

    private Predicate<PriceStatistics> withoutZeroStats() {
        return value -> !value.getMin().equals(INTEGER_ZERO) && !value.getMedian().equals(INTEGER_ZERO);
    }

    private Comparator<Long> byTimeStamp() {
        return Long::compare;
    }
}
