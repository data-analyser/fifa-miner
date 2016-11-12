package com.fifaminer.service.price.impl;

import com.fifaminer.service.price.BuyPriceStrategy;
import com.fifaminer.service.price.model.BuyPriceDefinitionContext;
import org.springframework.stereotype.Component;

@Component
public class LowestFromMinimalsStrategy implements BuyPriceStrategy {

    @Override
    public Integer calculate(BuyPriceDefinitionContext context) {
        Integer currentMin = context.getCurrentMin();
        Integer forecastedMin = context.getForecastedMin();
        return forecastedMin < currentMin ? forecastedMin : currentMin;
    }
}
