package com.fifaminer.service.impl;

import com.fifaminer.service.*;
import com.fifaminer.statistics.model.PriceStatistics;
import com.fifaminer.timeseries.TimeSeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

@Service
public class PriceServiceIml implements PriceService {

    private final PriceHistoryService priceHistoryService;
    private final PriceStatisticsService priceStatisticsService;
    private final TimeSeriesService timeSeriesService;

    @Autowired
    public PriceServiceIml(PriceHistoryService priceHistoryService,
                           PriceStatisticsService priceStatisticsService,
                           TimeSeriesService timeSeriesService) {
        this.priceHistoryService = priceHistoryService;
        this.priceStatisticsService = priceStatisticsService;
        this.timeSeriesService = timeSeriesService;
    }

    @Override
    public Integer getBuyPrice(Long playerId) {
        return 0;
    }

    @Override
    public Integer getSellPrice(Long playerId) {
        List<PriceStatistics> priceStatistics = calculateStatistics(priceHistoryService.findByPlayerId(playerId).getHistory());

        Double forecastedMin = timeSeriesService.forecast(
                extractProperty(priceStatistics, value -> value.getMin().doubleValue())
        );

        Double forecastedMedian = timeSeriesService.forecast(
                extractProperty(priceStatistics, value -> value.getMedian().doubleValue())
        );
        return 0;
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
                .filter(hasNoZeroStats())
                .collect(toList());
    }

    private Predicate<PriceStatistics> hasNoZeroStats() {
        return value -> !value.getMin().equals(INTEGER_ZERO) && !value.getMedian().equals(INTEGER_ZERO);
    }

    private List<Integer> getPrices(Map<Integer, Integer> prices) {
        return prices.keySet()
                .stream()
                .collect(toList());
    }
}
