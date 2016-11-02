package com.fifaminer.service.price.policy.impl;

import com.fifaminer.timeseries.util.Percentage;
import org.springframework.stereotype.Component;

@Component
public class SellPriceDefinitionPolicy {

    private static final int SELL_PRICE_PERCENTAGE = 98;

    public Integer define(Double forecastedMedian) {
        return Percentage.from(forecastedMedian.intValue(), SELL_PRICE_PERCENTAGE);
    }
}
