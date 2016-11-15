package com.fifaminer.service.price.impl;

import com.fifaminer.service.price.MaxBuyPriceStrategy;
import com.fifaminer.service.price.model.MaxBuyPriceDefinitionContext;
import com.fifaminer.util.Percentage;
import org.springframework.stereotype.Component;

@Component
public class Lower10ThanCurrentMinStrategy implements MaxBuyPriceStrategy {

    private static final Integer PERCENTS = 10;

    @Override
    public Integer calculate(MaxBuyPriceDefinitionContext context) {
        Integer currentMin = context.getCurrentMin();
        return currentMin - Percentage.from(currentMin, PERCENTS);
    }
}
