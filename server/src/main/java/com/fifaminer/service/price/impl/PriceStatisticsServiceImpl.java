package com.fifaminer.service.price.impl;

import com.fifaminer.service.price.PriceStatisticsService;
import com.fifaminer.statistics.StatisticsService;
import com.fifaminer.service.price.model.PriceStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.IntStream;

import static java.util.Collections.min;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.MapUtils.isEmpty;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

@Service
public class PriceStatisticsServiceImpl implements PriceStatisticsService {

    private final StatisticsService statisticsService;

    @Autowired
    public PriceStatisticsServiceImpl(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @Override
    public PriceStatistics calculatePriceStatistics(Long timestamp, Map<Integer, Integer> priceDistribution) {
        if (isEmpty(priceDistribution)) {
            return new PriceStatistics(timestamp, INTEGER_ZERO, INTEGER_ZERO);
        }
        return new PriceStatistics(
                timestamp,
                min(priceDistribution.keySet()),
                statisticsService.median(toVector(priceDistribution))
        );
    }

    private List<Integer> toVector(Map<Integer, Integer> priceDistribution) {
        return priceDistribution.entrySet().stream()
                .flatMap(entry -> expand(entry).stream())
                .collect(toList());
    }

    private List<Integer> expand(Entry<Integer, Integer> entry) {
        return IntStream.generate(entry::getKey)
                .limit(entry.getValue())
                .boxed()
                .collect(toList());
    }
}
