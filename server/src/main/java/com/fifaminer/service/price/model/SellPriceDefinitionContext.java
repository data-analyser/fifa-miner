package com.fifaminer.service.price.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public final class SellPriceDefinitionContext {

    private final Integer forecastedMin;
    private final Integer forecastedMedian;
    private final List<PriceStatistics> priceStatistics;
}
