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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.fifaminer.service.setting.type.Setting.*;
import static java.lang.Long.compare;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.MapUtils.isEmpty;
import static org.apache.commons.collections4.MapUtils.isNotEmpty;
import static org.apache.commons.lang3.math.NumberUtils.*;

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
        PriceHistory priceHistory = priceHistoryService.findByPlayerId(playerId);

        if (isNull(priceHistory) || isEmpty(priceHistory.getHistory())) return INTEGER_ZERO;

        List<PriceStatistics> priceStatistics = calculateStatistics(
                priceHistory.getHistory()
        );

        Double forecastedMin = timeSeriesService.forecast(
                extractProperty(priceStatistics, value -> value.getMin().doubleValue())
        );
        return maxBuyPolicy.define(priceStatistics, forecastedMin);
    }

    @Override
    public Integer getSellStartPrice(Long playerId) {
        return sellStartPolicy.define(getSellBuyNowPrice(playerId));
    }

    @Override
    public Integer getSellBuyNowPrice(Long playerId) {
        PriceHistory priceHistory = priceHistoryService.findByPlayerId(playerId);

        if (isNull(priceHistory) || isEmpty(priceHistory.getHistory())) return INTEGER_ZERO;

        List<PriceStatistics> priceStatistics = calculateStatistics(
                priceHistory.getHistory()
        );

        Double forecastedMedian = timeSeriesService.forecast(
                extractProperty(priceStatistics, value -> value.getMedian().doubleValue())
        );

        Double forecastedMin = timeSeriesService.forecast(
                extractProperty(priceStatistics, value -> value.getMin().doubleValue())
        );
        return sellBuyNowPolicy.define(forecastedMin, forecastedMedian, priceStatistics);
    }

    @Override
    public Integer getBuyNowProfit(Long playerId) {
        return taxService.reduceTax(getSellBuyNowPrice(playerId)) - getMaxBuyPrice(playerId);
    }

    @Override
    public Integer getStartProfit(Long playerId) {
        return taxService.reduceTax(getSellStartPrice(playerId)) - getMaxBuyPrice(playerId);
    }

    @Override
    public PlayerPrice getPricesSummary(Long playerId) {
        Integer maxBuyNowPrice = getMaxBuyPrice(playerId);
        Integer sellBuyNowPrice = getSellBuyNowPrice(playerId);
        Integer sellStartPrice = sellStartPolicy.define(sellBuyNowPrice);
        Integer startProfit = taxService.reduceTax(sellStartPrice) - maxBuyNowPrice;
        Integer buyNowProfit = taxService.reduceTax(sellBuyNowPrice) - maxBuyNowPrice;

        return new PlayerPrice(
                playerId, maxBuyNowPrice, sellStartPrice,
                sellBuyNowPrice, startProfit, buyNowProfit,
                settingsService.getSetting(MAX_BUY_PRICE_STRATEGY),
                settingsService.getSetting(SELL_START_PRICE_STRATEGY),
                settingsService.getSetting(SELL_BUY_NOW_PRICE_STRATEGY)
        );
    }

    @Override
    public boolean isPriceDistributionActual(Long playerId) {
        return priceActualityPolicy.isActualPrices(getLastDistributionTime(playerId));
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
                .map(entry -> priceStatisticsService.calculatePriceStatistics(entry.getKey(), getPrices(entry.getValue())))
                .sorted((current, next) -> compare(current.getTimestamp(), next.getTimestamp()))
                .filter(withoutZeroStats())
                .collect(toList());
    }

    private Predicate<PriceStatistics> withoutZeroStats() {
        return value -> !value.getMin().equals(INTEGER_ZERO) && !value.getMedian().equals(INTEGER_ZERO);
    }

    private List<Integer> getPrices(Map<Integer, Integer> prices) {
        return prices.keySet()
                .stream()
                .collect(toList());
    }

    private Comparator<Long> byTimeStamp() {
        return Long::compare;
    }
}
