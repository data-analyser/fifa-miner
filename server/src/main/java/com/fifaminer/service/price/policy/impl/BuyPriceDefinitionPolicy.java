package com.fifaminer.service.price.policy.impl;

import com.fifaminer.service.price.PriceBoundService;
import com.fifaminer.service.price.model.PriceStatistics;
import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuyPriceDefinitionPolicy {

    private final PriceBoundService priceBoundService;

    @Autowired
    public BuyPriceDefinitionPolicy(PriceBoundService priceBoundService) {
        this.priceBoundService = priceBoundService;
    }

    public Integer define(List<PriceStatistics> priceStatistics, Double forecastedMin) {
        Integer currentMin = Iterables.getLast(priceStatistics)
                .getMin();
        Integer buyPrice = forecastedMin < currentMin ? forecastedMin.intValue() : currentMin;
        return priceBoundService.arrangeToBound(buyPrice);
    }
}
