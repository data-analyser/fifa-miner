package com.fifaminer.service.price.impl;

import com.fifaminer.service.price.MaxBuyPriceStrategy;
import com.fifaminer.service.price.model.MaxBuyPriceDefinitionContext;
import org.springframework.stereotype.Component;

@Component
public class LowestFromMinimalsStrategy implements MaxBuyPriceStrategy {

    @Override
    public Integer calculate(MaxBuyPriceDefinitionContext context) {
        Integer currentMin = context.getCurrentMin();
        Integer forecastedMin = context.getForecastedMin();
        return forecastedMin < currentMin ? forecastedMin : currentMin;
    }
}
