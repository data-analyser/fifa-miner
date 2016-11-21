package com.fifaminer.service.price.strategy.impl;

import com.fifaminer.service.price.model.MaxBuyPriceDefinitionContext;
import com.fifaminer.service.price.strategy.MaxBuyPriceStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Reduce30FromCurrentMinStrategy implements MaxBuyPriceStrategy {

    private final PriceReducingStrategy priceReducingStrategy;

    private static final int REDUCING_PERCENTS = 30;

    @Autowired
    public Reduce30FromCurrentMinStrategy(PriceReducingStrategy priceReducingStrategy) {
        this.priceReducingStrategy = priceReducingStrategy;
    }

    @Override
    public Integer calculate(MaxBuyPriceDefinitionContext context) {
        return priceReducingStrategy.reduce(context.getCurrentMin(), REDUCING_PERCENTS);
    }
}
