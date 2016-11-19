package com.fifaminer.service.price;

import com.fifaminer.service.price.model.PriceStatistics;

import java.util.Map;

public interface PriceStatisticsService {

    PriceStatistics calculatePriceStatistics(Long timestamp, Map<Integer, Integer> priceDistribution);
}
