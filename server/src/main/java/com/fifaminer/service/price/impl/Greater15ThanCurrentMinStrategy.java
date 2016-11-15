package com.fifaminer.service.price.impl;

import com.fifaminer.service.price.SellBuyNowPriceStrategy;
import com.fifaminer.service.price.model.SellBuyNowPriceDefinitionContext;
import com.fifaminer.util.Percentage;
import com.google.common.collect.Iterables;
import org.springframework.stereotype.Component;

@Component
public class Greater15ThanCurrentMinStrategy implements SellBuyNowPriceStrategy {

    private static final Integer PERCENTS = 15;

    @Override
    public Integer calculate(SellBuyNowPriceDefinitionContext context) {
        Integer lastMin = Iterables.getLast(context.getPriceStatistics())
                .getMin();
        return lastMin + Percentage.from(lastMin, PERCENTS);
    }
}
