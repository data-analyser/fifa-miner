package com.fifaminer.service.price.strategy.impl;

import com.fifaminer.service.price.PriceBoundService;
import com.fifaminer.service.price.model.SellBuyNowPriceDefinitionContext;
import com.fifaminer.service.price.strategy.SellBuyNowPriceStrategy;
import com.fifaminer.service.price.type.BoundSelection;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.Collections.min;

@Component
public class MicroTradeSellStrategy implements SellBuyNowPriceStrategy {

    private final PriceBoundService priceBoundService;

    public static final int MAX_MICRO_TRADE_PRICE = 3000;
    private static final int MICRO_TRADE_PROFIT_IN_BIDS = 3;

    @Autowired
    public MicroTradeSellStrategy(PriceBoundService priceBoundService) {
        this.priceBoundService = priceBoundService;
    }

    @Override
    public Integer calculate(SellBuyNowPriceDefinitionContext context) {
        Integer minPrice = min(context.getPricesDistribution().keySet());
        Preconditions.checkState(minPrice < MAX_MICRO_TRADE_PRICE, "Cannot process price with this strategy");
        return priceBoundService.arrangeToSteps(minPrice, MICRO_TRADE_PROFIT_IN_BIDS, BoundSelection.HIGHER);
    }
}
