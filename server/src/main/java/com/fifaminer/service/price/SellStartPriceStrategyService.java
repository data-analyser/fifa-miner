package com.fifaminer.service.price;

import com.fifaminer.service.price.strategy.SellStartPriceStrategy;

public interface SellStartPriceStrategyService {

    SellStartPriceStrategy findActiveSellStartStrategy();
}
