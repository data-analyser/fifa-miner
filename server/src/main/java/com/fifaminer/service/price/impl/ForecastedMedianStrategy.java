package com.fifaminer.service.price.impl;

import com.fifaminer.service.price.SellBuyNowPriceStrategy;
import com.fifaminer.service.price.model.SellBuyNowPriceDefinitionContext;
import org.springframework.stereotype.Component;

@Component
public class ForecastedMedianStrategy implements SellBuyNowPriceStrategy {

    @Override
    public Integer calculate(SellBuyNowPriceDefinitionContext context) {
        return context.getForecastedMedian();
    }
}
