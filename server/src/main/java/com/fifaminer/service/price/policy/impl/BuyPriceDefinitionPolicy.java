package com.fifaminer.service.price.policy.impl;

import com.fifaminer.service.price.BuyPriceStrategyService;
import com.fifaminer.service.price.PriceBoundService;
import com.fifaminer.service.price.model.BuyPriceDefinitionContext;
import com.fifaminer.service.price.model.PriceStatistics;
import com.fifaminer.service.price.type.BoundSelection;
import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuyPriceDefinitionPolicy {

    private final PriceBoundService priceBoundService;
    private final BuyPriceStrategyService buyPriceStrategyService;

    @Autowired
    public BuyPriceDefinitionPolicy(PriceBoundService priceBoundService,
                                    BuyPriceStrategyService buyPriceStrategyService) {
        this.priceBoundService = priceBoundService;
        this.buyPriceStrategyService = buyPriceStrategyService;
    }

    public Integer define(List<PriceStatistics> priceStatistics, Double forecastedMin) {
        Integer currentMin = Iterables.getLast(priceStatistics)
                .getMin();

        Integer buyPriceByStrategy = buyPriceStrategyService.findActiveBuyStrategy()
                .calculate(new BuyPriceDefinitionContext(forecastedMin.intValue(), currentMin));
        return priceBoundService.arrangeToBound(buyPriceByStrategy, BoundSelection.LOWER);
    }
}
