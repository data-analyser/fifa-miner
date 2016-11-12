package com.fifaminer.service.price.policy.impl;

import com.fifaminer.service.price.PriceBoundService;
import com.fifaminer.service.price.SellPriceStrategyService;
import com.fifaminer.service.price.TaxService;
import com.fifaminer.service.price.model.SellPriceDefinitionContext;
import com.fifaminer.service.price.model.PriceStatistics;
import com.fifaminer.service.price.type.BoundSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SellPriceDefinitionPolicy {

    private final TaxService taxService;
    private final PriceBoundService priceBoundService;
    private final SellPriceStrategyService sellPriceStrategyService;

    @Autowired
    public SellPriceDefinitionPolicy(TaxService taxService,
                                     PriceBoundService priceBoundService,
                                     SellPriceStrategyService sellPriceStrategyService) {
        this.taxService = taxService;
        this.priceBoundService = priceBoundService;
        this.sellPriceStrategyService = sellPriceStrategyService;
    }

    public Integer define(Double forecastedMin,
                          Double forecastedMedian,
                          List<PriceStatistics> priceStatistics) {
        SellPriceDefinitionContext sellPriceDefinitionContext = new SellPriceDefinitionContext(
                forecastedMin.intValue(), forecastedMedian.intValue(), priceStatistics);

        Integer sellPriceByStrategy = sellPriceStrategyService.findActiveSellStrategy()
                .calculate(sellPriceDefinitionContext);

        return priceBoundService.arrangeToBound(
                taxService.addTax(sellPriceByStrategy), BoundSelection.HIGHER
        );
    }
}
