package com.fifaminer.service.price.impl;

import com.fifaminer.entity.PriceHistory;
import com.fifaminer.service.price.*;
import com.fifaminer.service.price.model.PlayerPrice;
import com.fifaminer.service.price.policy.BidPriceDefinitionPolicy;
import com.fifaminer.service.price.policy.BuyPriceDefinitionPolicy;
import com.fifaminer.service.price.policy.SellPriceDefinitionPolicy;
import com.fifaminer.service.price.model.PriceStatistics;
import com.fifaminer.service.setting.SettingsService;
import com.fifaminer.timeseries.TimeSeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.fifaminer.service.setting.type.Setting.*;
import static java.lang.Long.compare;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.MapUtils.isEmpty;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

@Service
public class PriceServiceIml implements PriceService {

    private final PriceHistoryService priceHistoryService;
    private final PriceStatisticsService priceStatisticsService;
    private final TimeSeriesService timeSeriesService;
    private final BuyPriceDefinitionPolicy buyPolicy;
    private final SellPriceDefinitionPolicy sellPolicy;
    private final BidPriceDefinitionPolicy bidPolicy;
    private final TaxService taxService;
    private final SettingsService settingsService;

    @Autowired
    public PriceServiceIml(PriceHistoryService priceHistoryService,
                           PriceStatisticsService priceStatisticsService,
                           TimeSeriesService timeSeriesService,
                           BuyPriceDefinitionPolicy buyPolicy,
                           SellPriceDefinitionPolicy sellPolicy,
                           BidPriceDefinitionPolicy bidPolicy,
                           TaxService taxService,
                           SettingsService settingsService) {
        this.priceHistoryService = priceHistoryService;
        this.priceStatisticsService = priceStatisticsService;
        this.timeSeriesService = timeSeriesService;
        this.buyPolicy = buyPolicy;
        this.sellPolicy = sellPolicy;
        this.bidPolicy = bidPolicy;
        this.taxService = taxService;
        this.settingsService = settingsService;
    }

    @Override
    public Integer getBuyPrice(Long playerId) {
        PriceHistory priceHistory = priceHistoryService.findByPlayerId(playerId);

        if (isNull(priceHistory) || isEmpty(priceHistory.getHistory())) return INTEGER_ZERO;

        List<PriceStatistics> priceStatistics = calculateStatistics(
                priceHistory.getHistory()
        );

        Double forecastedMin = timeSeriesService.forecast(
                extractProperty(priceStatistics, value -> value.getMin().doubleValue())
        );
        return buyPolicy.define(priceStatistics, forecastedMin);
    }

    @Override
    public Integer getSellPrice(Long playerId) {
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
        return sellPolicy.define(forecastedMin, forecastedMedian, priceStatistics);
    }

    @Override
    public Integer getBidPrice(Long playerId) {
        return bidPolicy.define(getSellPrice(playerId));
    }

    @Override
    public Integer getProfit(Long playerId) {
        return taxService.reduceTax(getSellPrice(playerId)) - getBuyPrice(playerId);
    }

    @Override
    public PlayerPrice getPlayerPriceInfo(Long playerId) {
        return new PlayerPrice(
                playerId,
                getBuyPrice(playerId),
                getSellPrice(playerId),
                getBidPrice(playerId),
                getProfit(playerId),
                settingsService.getSetting(BUY_PRICE_STRATEGY),
                settingsService.getSetting(SELL_PRICE_STRATEGY),
                settingsService.getSetting(BID_PRICE_STRATEGY)
        );
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
}
