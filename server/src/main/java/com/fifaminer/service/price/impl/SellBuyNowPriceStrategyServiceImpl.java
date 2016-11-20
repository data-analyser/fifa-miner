package com.fifaminer.service.price.impl;

import com.fifaminer.service.price.SellBuyNowPriceStrategy;
import com.fifaminer.service.price.SellBuyNowPriceStrategyService;
import com.fifaminer.service.setting.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.fifaminer.service.setting.type.Setting.SELL_BUY_NOW_PRICE_STRATEGY;

@Service
public class SellBuyNowPriceStrategyServiceImpl implements SellBuyNowPriceStrategyService {

    private final SettingsService settingsService;
    private final Map<String, SellBuyNowPriceStrategy> sellBuyNowPriceStrategies;

    private static final String DEFAULT_STRATEGY = "oneBidLessThanFirstMaximum";

    @Autowired
    public SellBuyNowPriceStrategyServiceImpl(SettingsService settingsService,
                                              Map<String, SellBuyNowPriceStrategy> sellBuyNowPriceStrategies) {
        this.settingsService = settingsService;
        this.sellBuyNowPriceStrategies = sellBuyNowPriceStrategies;
    }

    @Override
    public SellBuyNowPriceStrategy findActiveSellBuyNowStrategy() {
        String activeSellBuyNowPriceStrategy = settingsService.getSetting(SELL_BUY_NOW_PRICE_STRATEGY);
        return sellBuyNowPriceStrategies.getOrDefault(
                activeSellBuyNowPriceStrategy, sellBuyNowPriceStrategies.get(DEFAULT_STRATEGY)
        );
    }
}
