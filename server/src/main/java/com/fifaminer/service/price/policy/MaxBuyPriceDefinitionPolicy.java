package com.fifaminer.service.price.policy;

import com.fifaminer.service.price.MaxBuyPriceStrategyService;
import com.fifaminer.service.price.PriceBoundService;
import com.fifaminer.service.price.model.MaxBuyPriceDefinitionContext;
import com.fifaminer.service.price.model.PriceStatistics;
import com.fifaminer.service.price.type.BoundSelection;
import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MaxBuyPriceDefinitionPolicy {

    private final PriceBoundService priceBoundService;
    private final MaxBuyPriceStrategyService maxBuyPriceStrategyService;

    @Autowired
    public MaxBuyPriceDefinitionPolicy(PriceBoundService priceBoundService,
                                       MaxBuyPriceStrategyService maxBuyPriceStrategyService) {
        this.priceBoundService = priceBoundService;
        this.maxBuyPriceStrategyService = maxBuyPriceStrategyService;
    }

    public Integer define(List<PriceStatistics> priceStatistics, Double forecastedMin) {
        Integer currentMin = Iterables.getLast(priceStatistics)
                .getMin();

        Integer maxBuyPriceByStrategy = maxBuyPriceStrategyService.findActiveBuyStrategy()
                .calculate(new MaxBuyPriceDefinitionContext(forecastedMin.intValue(), currentMin));
        return priceBoundService.arrangeToBound(maxBuyPriceByStrategy, BoundSelection.LOWER);
    }
}
