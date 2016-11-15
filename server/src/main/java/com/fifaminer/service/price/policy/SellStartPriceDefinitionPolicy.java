package com.fifaminer.service.price.policy;


import com.fifaminer.service.price.SellStartPriceStrategyService;
import com.fifaminer.service.price.PriceBoundService;
import com.fifaminer.service.price.type.BoundSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SellStartPriceDefinitionPolicy {

    private final PriceBoundService priceBoundService;
    private final SellStartPriceStrategyService sellStartPriceStrategyService;

    @Autowired
    public SellStartPriceDefinitionPolicy(PriceBoundService priceBoundService,
                                          SellStartPriceStrategyService sellStartPriceStrategyService) {
        this.priceBoundService = priceBoundService;
        this.sellStartPriceStrategyService = sellStartPriceStrategyService;
    }

    public Integer define(Integer sellBuyNowPrice) {
        Integer sellStartPriceByStrategy = sellStartPriceStrategyService.findActiveSellStartStrategy()
                .calculate(sellBuyNowPrice);
        return priceBoundService.arrangeToBound(sellStartPriceByStrategy, BoundSelection.HIGHER);
    }
}
