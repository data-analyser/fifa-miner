package com.fifaminer.service.price;

import com.fifaminer.service.price.strategy.MaxBuyPriceStrategy;

public interface MaxBuyPriceStrategyService {

    MaxBuyPriceStrategy findActiveBuyStrategy();
}
