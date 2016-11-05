package com.fifaminer.service.price;

import com.fifaminer.service.price.model.PriceStatistics;

import java.util.List;

public interface PriceStatisticsService {

    PriceStatistics calculatePriceStatistics(Long timestamp, List<Integer> prices);
}
