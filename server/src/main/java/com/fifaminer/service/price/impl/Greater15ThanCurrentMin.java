package com.fifaminer.service.price.impl;

import com.fifaminer.service.price.SellPriceStrategy;
import com.fifaminer.service.price.model.SellPriceDefinitionContext;
import com.fifaminer.util.Percentage;
import com.google.common.collect.Iterables;
import org.springframework.stereotype.Component;

@Component
public class Greater15ThanCurrentMin implements SellPriceStrategy {

    private static final Integer PERCENTS = 15;

    @Override
    public Integer calculate(SellPriceDefinitionContext context) {
        Integer lastMin = Iterables.getLast(context.getPriceStatistics())
                .getMin();
        return lastMin + Percentage.from(lastMin, PERCENTS);
    }
}
