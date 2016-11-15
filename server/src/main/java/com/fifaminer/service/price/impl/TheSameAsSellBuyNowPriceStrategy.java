package com.fifaminer.service.price.impl;

import com.fifaminer.service.price.SellStartPriceStrategy;
import org.springframework.stereotype.Component;

@Component
public class TheSameAsSellBuyNowPriceStrategy implements SellStartPriceStrategy {

    @Override
    public Integer calculate(Integer sellBuyNowPrice) {
        return sellBuyNowPrice;
    }
}
