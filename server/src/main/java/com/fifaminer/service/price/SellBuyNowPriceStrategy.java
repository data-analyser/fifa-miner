package com.fifaminer.service.price;

import com.fifaminer.service.price.model.SellBuyNowPriceDefinitionContext;

public interface SellBuyNowPriceStrategy {

    Integer calculate(SellBuyNowPriceDefinitionContext context);
}
