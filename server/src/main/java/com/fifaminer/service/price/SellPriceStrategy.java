package com.fifaminer.service.price;

import com.fifaminer.service.price.model.PriceDefinitionContext;

public interface SellPriceStrategy {

    Integer calculate(PriceDefinitionContext context);
}
