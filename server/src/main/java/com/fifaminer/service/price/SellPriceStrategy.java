package com.fifaminer.service.price;

import com.fifaminer.service.price.model.SellPriceDefinitionContext;

public interface SellPriceStrategy {

    Integer calculate(SellPriceDefinitionContext context);
}
