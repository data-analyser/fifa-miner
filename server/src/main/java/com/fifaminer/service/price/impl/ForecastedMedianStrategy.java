package com.fifaminer.service.price.impl;

import com.fifaminer.service.price.SellPriceStrategy;
import com.fifaminer.service.price.model.PriceDefinitionContext;
import org.springframework.stereotype.Component;

@Component
public class ForecastedMedianStrategy implements SellPriceStrategy {

    @Override
    public Integer calculate(PriceDefinitionContext context) {
        return context.getForecastedMedian();
    }
}
