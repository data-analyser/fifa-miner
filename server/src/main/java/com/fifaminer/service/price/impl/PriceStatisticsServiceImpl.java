package com.fifaminer.service.price.impl;

import com.fifaminer.service.price.PriceStatisticsService;
import com.fifaminer.statistics.StatisticsService;
import com.fifaminer.statistics.model.PriceStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Collections.min;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

@Service
public class PriceStatisticsServiceImpl implements PriceStatisticsService {

    private final StatisticsService statisticsService;

    @Autowired
    public PriceStatisticsServiceImpl(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @Override
    public PriceStatistics calculatePriceStatistics(Long timestamp, List<Integer> prices) {
        if (isEmpty(prices)) {
            return new PriceStatistics(timestamp, INTEGER_ZERO, INTEGER_ZERO);
        }
        return new PriceStatistics(timestamp, min(prices), statisticsService.median(prices));
    }
}
