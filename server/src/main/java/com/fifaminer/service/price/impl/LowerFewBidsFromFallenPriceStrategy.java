package com.fifaminer.service.price.impl;

import com.fifaminer.service.price.MaxBuyPriceStrategy;
import com.fifaminer.service.price.PriceBoundService;
import com.fifaminer.service.price.model.MaxBuyPriceDefinitionContext;
import com.fifaminer.service.price.type.BoundSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: Dimitr
 * Date: 17.11.2016
 * Time: 20:25
 */
@Component
public class LowerFewBidsFromFallenPriceStrategy implements MaxBuyPriceStrategy {

    private final PriceBoundService priceBoundService;

    private static final Integer BID_STEPS = 2;

    @Autowired
    public LowerFewBidsFromFallenPriceStrategy(PriceBoundService priceBoundService) {
        this.priceBoundService = priceBoundService;
    }

    @Override
    public Integer calculate(MaxBuyPriceDefinitionContext context) {
        Integer currentMin = context.getCurrentMin();
        Integer forecastedMin = context.getForecastedMin();

        if (isNegativeTrend(currentMin, forecastedMin)) {
            return priceBoundService.arrangeToSteps(forecastedMin, BID_STEPS, BoundSelection.LOWER);
        }
        return currentMin;
    }

    private boolean isNegativeTrend(Integer currentMin, Integer forecastedMin) {
        return forecastedMin < currentMin;
    }
}
