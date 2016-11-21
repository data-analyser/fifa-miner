package com.fifaminer.service.price.strategy.impl;

import com.fifaminer.service.price.model.MaxBuyPriceDefinitionContext;
import com.fifaminer.service.price.strategy.MaxBuyPriceStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Reduce10FromCurrentMinStrategy implements MaxBuyPriceStrategy {

    private final PriceReducingStrategy priceReducingStrategy;

    private static final int REDUCING_PERCENTS = 10;

    @Autowired
    public Reduce10FromCurrentMinStrategy(PriceReducingStrategy priceReducingStrategy) {
        this.priceReducingStrategy = priceReducingStrategy;
    }

    @Override
    public Integer calculate(MaxBuyPriceDefinitionContext context) {
        return priceReducingStrategy.reduce(context.getCurrentMin(), REDUCING_PERCENTS);
    }
}
