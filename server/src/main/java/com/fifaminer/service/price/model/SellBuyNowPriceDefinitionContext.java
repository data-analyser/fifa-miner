package com.fifaminer.service.price.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public final class SellBuyNowPriceDefinitionContext {

    private final Integer forecastedMin;
    private final Integer forecastedMedian;
    private final Map<Integer, Integer> pricesDistribution;
}
