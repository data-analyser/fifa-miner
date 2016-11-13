package com.fifaminer.service.price.policy;


import com.fifaminer.service.price.BidPriceStrategyService;
import com.fifaminer.service.price.PriceBoundService;
import com.fifaminer.service.price.type.BoundSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BidPriceDefinitionPolicy {

    private final PriceBoundService priceBoundService;
    private final BidPriceStrategyService bidPriceStrategyService;

    @Autowired
    public BidPriceDefinitionPolicy(PriceBoundService priceBoundService,
                                    BidPriceStrategyService bidPriceStrategyService) {
        this.priceBoundService = priceBoundService;
        this.bidPriceStrategyService = bidPriceStrategyService;
    }

    public Integer define(Integer sellPrice) {
        Integer bidPrice = bidPriceStrategyService.findActiveBidStrategy()
                .calculate(sellPrice);
        return priceBoundService.arrangeToBound(bidPrice, BoundSelection.HIGHER);
    }
}
