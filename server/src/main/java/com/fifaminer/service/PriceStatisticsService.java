package com.fifaminer.service;

import com.fifaminer.statistics.model.PriceStatistics;

import java.util.List;

public interface PriceStatisticsService {

    PriceStatistics calculatePriceStatistics(Long timestamp, List<Integer> prices);
}
