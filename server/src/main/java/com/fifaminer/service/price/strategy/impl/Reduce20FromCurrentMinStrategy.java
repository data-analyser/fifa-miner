package com.fifaminer.service.price.strategy.impl;

import com.fifaminer.service.price.strategy.MaxBuyPriceStrategy;
import com.fifaminer.service.price.model.MaxBuyPriceDefinitionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Reduce20FromCurrentMinStrategy implements MaxBuyPriceStrategy {

    private final PriceReducingStrategy priceReducingStrategy;

    private static final int REDUCING_PERCENTS = 20;

    @Autowired
    public Reduce20FromCurrentMinStrategy(PriceReducingStrategy priceReducingStrategy) {
        this.priceReducingStrategy = priceReducingStrategy;
    }

    @Override
    public Integer calculate(MaxBuyPriceDefinitionContext context) {
        return priceReducingStrategy.reduce(context.getCurrentMin(), REDUCING_PERCENTS);
    }
}
