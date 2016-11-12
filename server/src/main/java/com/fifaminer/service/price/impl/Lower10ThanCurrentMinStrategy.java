package com.fifaminer.service.price.impl;

import com.fifaminer.service.price.BuyPriceStrategy;
import com.fifaminer.service.price.model.BuyPriceDefinitionContext;
import com.fifaminer.util.Percentage;
import org.springframework.stereotype.Component;

@Component
public class Lower10ThanCurrentMinStrategy implements BuyPriceStrategy {

    private static final Integer PERCENTS = 10;

    @Override
    public Integer calculate(BuyPriceDefinitionContext context) {
        Integer currentMin = context.getCurrentMin();
        return currentMin - Percentage.from(currentMin, PERCENTS);
    }
}
