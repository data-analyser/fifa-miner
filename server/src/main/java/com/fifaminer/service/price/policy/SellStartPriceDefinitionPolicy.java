package com.fifaminer.service.price.policy;


import com.fifaminer.service.price.SellStartPriceStrategyService;
import com.fifaminer.service.price.PriceBoundService;
import com.fifaminer.service.price.type.BoundSelection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
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
        Integer price = priceBoundService.arrangeToBound(sellStartPriceByStrategy, BoundSelection.HIGHER);
        log.info("Calculated sell start price = {} by sell buy now price = {}", price, sellBuyNowPrice);
        return price;
    }
}
