package com.fifaminer.service.price.strategy;

import com.fifaminer.service.price.model.SellBuyNowPriceDefinitionContext;

public interface SellBuyNowPriceStrategy {

    Integer calculate(SellBuyNowPriceDefinitionContext context);
}
