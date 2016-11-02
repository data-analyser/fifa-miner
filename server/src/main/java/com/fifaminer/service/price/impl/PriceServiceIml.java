package com.fifaminer.service.price.impl;

import com.fifaminer.service.price.PriceHistoryService;
import com.fifaminer.service.price.PriceService;
import com.fifaminer.service.price.PriceStatisticsService;
import com.fifaminer.service.price.policy.impl.BuyPriceDefinitionPolicy;
import com.fifaminer.service.price.policy.impl.SellPriceDefinitionPolicy;
import com.fifaminer.statistics.model.PriceStatistics;
import com.fifaminer.timeseries.TimeSeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.lang.Long.compare;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

@Service
public class PriceServiceIml implements PriceService {

    private final PriceHistoryService priceHistoryService;
    private final PriceStatisticsService priceStatisticsService;
    private final TimeSeriesService timeSeriesService;
    private final BuyPriceDefinitionPolicy buyPolicy;
    private final SellPriceDefinitionPolicy sellPolicy;

    @Autowired
    public PriceServiceIml(PriceHistoryService priceHistoryService,
                           PriceStatisticsService priceStatisticsService,
                           TimeSeriesService timeSeriesService,
                           BuyPriceDefinitionPolicy buyPolicy,
                           SellPriceDefinitionPolicy sellPolicy) {
        this.priceHistoryService = priceHistoryService;
        this.priceStatisticsService = priceStatisticsService;
        this.timeSeriesService = timeSeriesService;
        this.buyPolicy = buyPolicy;
        this.sellPolicy = sellPolicy;
    }

    @Override
    public Integer getBuyPrice(Long playerId) {
        List<PriceStatistics> priceStatistics = calculateStatistics(
                priceHistoryService.findByPlayerId(playerId).getHistory()
        );

        Double forecastedMin = timeSeriesService.forecast(
                extractProperty(priceStatistics, value -> value.getMin().doubleValue())
        );
        return buyPolicy.define(priceStatistics, forecastedMin);
    }

    @Override
    public Integer getSellPrice(Long playerId) {
        List<PriceStatistics> priceStatistics = calculateStatistics(
                priceHistoryService.findByPlayerId(playerId).getHistory()
        );

        Double forecastedMedian = timeSeriesService.forecast(
                extractProperty(priceStatistics, value -> value.getMedian().doubleValue())
        );
        return sellPolicy.define(forecastedMedian);
    }

    @Override
    public Integer getProfit(Long playerId) {
        return getSellPrice(playerId) - getBuyPrice(playerId);
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
