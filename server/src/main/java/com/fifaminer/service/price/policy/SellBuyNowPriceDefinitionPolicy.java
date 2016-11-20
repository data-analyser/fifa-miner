package com.fifaminer.service.price.policy;

import com.fifaminer.service.price.PriceBoundService;
import com.fifaminer.service.price.SellBuyNowPriceStrategyService;
import com.fifaminer.service.price.model.SellBuyNowPriceDefinitionContext;
import com.fifaminer.service.price.type.BoundSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SellBuyNowPriceDefinitionPolicy {

    private final PriceBoundService priceBoundService;
    private final SellBuyNowPriceStrategyService sellBuyNowPriceStrategyService;

    @Autowired
    public SellBuyNowPriceDefinitionPolicy(PriceBoundService priceBoundService,
                                           SellBuyNowPriceStrategyService sellBuyNowPriceStrategyService) {
        this.priceBoundService = priceBoundService;
        this.sellBuyNowPriceStrategyService = sellBuyNowPriceStrategyService;
    }

    public Integer define(Double forecastedMin,
                          Double forecastedMedian,
                          Map<Integer, Integer> lastPriceDistribution) {
        SellBuyNowPriceDefinitionContext sellBuyNowPriceDefinitionContext = new SellBuyNowPriceDefinitionContext(
                forecastedMin.intValue(), forecastedMedian.intValue(), lastPriceDistribution);

        Integer sellBuyNowPriceByStrategy = sellBuyNowPriceStrategyService.findActiveSellBuyNowStrategy()
                .calculate(sellBuyNowPriceDefinitionContext);

        return priceBoundService.arrangeToBound(sellBuyNowPriceByStrategy, BoundSelection.HIGHER);
    }
}
