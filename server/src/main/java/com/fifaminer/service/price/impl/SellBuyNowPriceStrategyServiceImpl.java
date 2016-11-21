package com.fifaminer.service.price.impl;

import com.fifaminer.service.price.strategy.SellBuyNowPriceStrategy;
import com.fifaminer.service.price.SellBuyNowPriceStrategyService;
import com.fifaminer.service.setting.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.fifaminer.client.dto.strategy.PriceStrategy.SELL_BUY_NOW_PRICE;

@Service
public class SellBuyNowPriceStrategyServiceImpl implements SellBuyNowPriceStrategyService {

    private final SettingsService settingsService;
    private final Map<String, SellBuyNowPriceStrategy> sellBuyNowPriceStrategies;

    private static final String DEFAULT_STRATEGY = "oneBidLessThanFirstMaximumStrategy";

    @Autowired
    public SellBuyNowPriceStrategyServiceImpl(SettingsService settingsService,
                                              Map<String, SellBuyNowPriceStrategy> sellBuyNowPriceStrategies) {
        this.settingsService = settingsService;
        this.sellBuyNowPriceStrategies = sellBuyNowPriceStrategies;
    }

    @Override
    public SellBuyNowPriceStrategy findActiveSellBuyNowStrategy() {
        String activeSellBuyNowPriceStrategy = settingsService.getSetting(SELL_BUY_NOW_PRICE.name());
        return sellBuyNowPriceStrategies.getOrDefault(
                activeSellBuyNowPriceStrategy, sellBuyNowPriceStrategies.get(DEFAULT_STRATEGY)
        );
    }
}
