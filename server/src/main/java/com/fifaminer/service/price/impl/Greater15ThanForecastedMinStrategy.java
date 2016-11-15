package com.fifaminer.service.price.impl;

import com.fifaminer.service.price.SellBuyNowPriceStrategy;
import com.fifaminer.service.price.model.SellBuyNowPriceDefinitionContext;
import com.fifaminer.util.Percentage;
import org.springframework.stereotype.Component;

@Component
public class Greater15ThanForecastedMinStrategy implements SellBuyNowPriceStrategy {

    private static final Integer PERCENTS = 15;

    @Override
    public Integer calculate(SellBuyNowPriceDefinitionContext context) {
        Integer forecastedMin = context.getForecastedMin();
        return forecastedMin + Percentage.from(forecastedMin, PERCENTS);
    }
}
