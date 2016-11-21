package com.fifaminer.service.price.strategy.impl;

import com.fifaminer.service.price.PriceBoundService;
import com.fifaminer.service.price.SellStartPriceStrategy;
import com.fifaminer.service.price.type.BoundSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LowerFewBidsFromBuyNowPriceStrategy implements SellStartPriceStrategy {

    private static final int BID_STEPS = 1;

    private final PriceBoundService priceBoundService;

    @Autowired
    public LowerFewBidsFromBuyNowPriceStrategy(PriceBoundService priceBoundService) {
        this.priceBoundService = priceBoundService;
    }

    @Override
    public Integer calculate(Integer sellBuyNowPrice) {
        return priceBoundService.arrangeToSteps(sellBuyNowPrice, BID_STEPS, BoundSelection.LOWER);
    }
}
