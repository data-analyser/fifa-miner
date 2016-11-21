package com.fifaminer.service.price.strategy.impl;

import com.fifaminer.service.price.PriceBoundService;
import com.fifaminer.service.price.type.BoundSelection;
import com.fifaminer.util.Percentage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PriceReducingStrategy {

    private final PriceBoundService priceBoundService;

    @Autowired
    public PriceReducingStrategy(PriceBoundService priceBoundService) {
        this.priceBoundService = priceBoundService;
    }

    public Integer reduce(Integer price, Integer percents) {
        return priceBoundService.arrangeToBound(
                price - Percentage.from(price, percents),
                BoundSelection.LOWER
        );
    }
}
