package com.fifaminer.service.price.impl;

import com.fifaminer.service.price.BidPriceStrategy;
import com.fifaminer.util.Percentage;
import org.springframework.stereotype.Component;

@Component
public class More10ThanSellPriceStrategy implements BidPriceStrategy {

    private static final int PERCENTS = 10;

    @Override
    public Integer calculate(Integer sellPrice) {
        return sellPrice + Percentage.from(sellPrice, PERCENTS);
    }
}
