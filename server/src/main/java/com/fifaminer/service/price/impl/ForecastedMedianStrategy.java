package com.fifaminer.service.price.impl;

import com.fifaminer.service.price.SellPriceStrategy;
import com.fifaminer.service.price.model.SellPriceDefinitionContext;
import org.springframework.stereotype.Component;

@Component
public class ForecastedMedianStrategy implements SellPriceStrategy {

    @Override
    public Integer calculate(SellPriceDefinitionContext context) {
        return context.getForecastedMedian();
    }
}
