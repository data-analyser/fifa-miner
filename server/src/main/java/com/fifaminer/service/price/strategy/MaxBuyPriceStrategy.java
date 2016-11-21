package com.fifaminer.service.price.strategy;

import com.fifaminer.service.price.model.MaxBuyPriceDefinitionContext;

public interface MaxBuyPriceStrategy {

    Integer calculate(MaxBuyPriceDefinitionContext context);
}
