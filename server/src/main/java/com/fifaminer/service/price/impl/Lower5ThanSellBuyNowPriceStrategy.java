package com.fifaminer.service.price.impl;

import com.fifaminer.service.price.SellStartPriceStrategy;
import com.fifaminer.util.Percentage;
import org.springframework.stereotype.Component;

@Component
public class Lower5ThanSellBuyNowPriceStrategy implements SellStartPriceStrategy {

    private static final int PERCENTS = 5;

    @Override
    public Integer calculate(Integer sellBuyNowPrice) {
        return sellBuyNowPrice - Percentage.from(sellBuyNowPrice, PERCENTS);
    }
}
