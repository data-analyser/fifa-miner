package com.fifaminer.service.price.impl;

import com.fifaminer.service.price.SellPriceStrategy;
import com.fifaminer.service.price.SellPriceStrategyService;
import com.fifaminer.service.setting.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.fifaminer.service.setting.type.Setting.SELL_PRICE_STRATEGY;

@Service
public class SellPriceStrategyServiceImpl implements SellPriceStrategyService {

    private final SettingsService settingsService;
    private final Map<String, SellPriceStrategy> sellPriceStrategies;

    private static final String DEFAULT_STRATEGY = "forecastedMedianStrategy";

    @Autowired
    public SellPriceStrategyServiceImpl(SettingsService settingsService,
                                        Map<String, SellPriceStrategy> sellPriceStrategies) {
        this.settingsService = settingsService;
        this.sellPriceStrategies = sellPriceStrategies;
    }

    @Override
    public SellPriceStrategy findActiveSellStrategy() {
        String activeSellePriceStrategy = settingsService.getSetting(SELL_PRICE_STRATEGY);
        return sellPriceStrategies.getOrDefault(
                activeSellePriceStrategy, sellPriceStrategies.get(DEFAULT_STRATEGY)
        );
    }
}
