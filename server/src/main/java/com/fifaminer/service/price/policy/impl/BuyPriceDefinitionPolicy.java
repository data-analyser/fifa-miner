package com.fifaminer.service.price.policy.impl;

import com.fifaminer.service.price.model.PriceStatistics;
import com.google.common.collect.Iterables;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuyPriceDefinitionPolicy {

    public Integer define(List<PriceStatistics> priceStatistics, Double forecastedMin) {
        Integer currentMin = Iterables.getLast(priceStatistics)
                .getMin();
        return forecastedMin < currentMin ? forecastedMin.intValue() : currentMin;
    }
}
