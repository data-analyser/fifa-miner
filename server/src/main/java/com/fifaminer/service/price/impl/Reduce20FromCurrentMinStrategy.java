package com.fifaminer.service.price.impl;

import com.fifaminer.service.price.MaxBuyPriceStrategy;
import com.fifaminer.service.price.PriceBoundService;
import com.fifaminer.service.price.model.MaxBuyPriceDefinitionContext;
import com.fifaminer.service.price.type.BoundSelection;
import com.fifaminer.util.Percentage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Reduce20FromCurrentMinStrategy implements MaxBuyPriceStrategy {

    private final PriceBoundService priceBoundService;

    private static final int REDUCING_PERCENTS = 20;

    @Autowired
    public Reduce20FromCurrentMinStrategy(PriceBoundService priceBoundService) {
        this.priceBoundService = priceBoundService;
    }

    @Override
    public Integer calculate(MaxBuyPriceDefinitionContext context) {
        Integer currentMin = context.getCurrentMin();
        return priceBoundService.arrangeToBound(
                currentMin - Percentage.from(currentMin, REDUCING_PERCENTS),
                BoundSelection.LOWER
        );
    }
}
