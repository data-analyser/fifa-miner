package com.fifaminer.service.price.impl;

import com.fifaminer.service.price.SellPriceStrategy;
import com.fifaminer.service.price.model.SellPriceDefinitionContext;
import com.fifaminer.util.Percentage;
import org.springframework.stereotype.Component;

@Component
public class Greater15ThanForecastedMinStrategy implements SellPriceStrategy {

    private static final Integer PERCENTS = 15;

    @Override
    public Integer calculate(SellPriceDefinitionContext context) {
        Integer forecastedMin = context.getForecastedMin();
        return forecastedMin + Percentage.from(forecastedMin, PERCENTS);
    }
}
