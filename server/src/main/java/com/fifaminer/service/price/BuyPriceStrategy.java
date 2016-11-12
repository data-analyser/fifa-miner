package com.fifaminer.service.price;

import com.fifaminer.service.price.model.BuyPriceDefinitionContext;

public interface BuyPriceStrategy {

    Integer calculate(BuyPriceDefinitionContext context);
}
