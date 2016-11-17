package com.fifaminer.service.price.impl;

import com.fifaminer.service.price.SellStartPriceStrategy;
import com.fifaminer.service.price.SellStartPriceStrategyService;
import com.fifaminer.service.setting.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.fifaminer.service.setting.type.Setting.SELL_START_PRICE_STRATEGY;

@Service
public class SellStartPriceStrategyServiceImpl implements SellStartPriceStrategyService {

    private final SettingsService settingsService;
    private final Map<String, SellStartPriceStrategy> sellStartPriceStrategies;

    private static final String DEFAULT_STRATEGY = "lowerFewBidsFromBuyNowPriceStrategy";

    @Autowired
    public SellStartPriceStrategyServiceImpl(SettingsService settingsService,
                                             Map<String, SellStartPriceStrategy> sellStartPriceStrategies) {
        this.settingsService = settingsService;
        this.sellStartPriceStrategies = sellStartPriceStrategies;
    }

    @Override
    public SellStartPriceStrategy findActiveSellStartStrategy() {
        String activeSellStartStrategy = settingsService.getSetting(SELL_START_PRICE_STRATEGY);
        return sellStartPriceStrategies.getOrDefault(
                activeSellStartStrategy, sellStartPriceStrategies.get(DEFAULT_STRATEGY)
        );
    }
}
