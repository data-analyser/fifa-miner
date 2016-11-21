package com.fifaminer.service.price;

import com.fifaminer.service.price.strategy.SellBuyNowPriceStrategy;

public interface SellBuyNowPriceStrategyService {

    SellBuyNowPriceStrategy findActiveSellBuyNowStrategy();
}
