package com.fifaminer.service.price.impl;

import com.fifaminer.service.price.MaxBuyPriceStrategy;
import com.fifaminer.service.price.MaxBuyPriceStrategyService;
import com.fifaminer.service.setting.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.fifaminer.service.setting.type.Setting.MAX_BUY_PRICE_STRATEGY;

@Service
public class MaxBuyPriceStrategyServiceImpl implements MaxBuyPriceStrategyService {

    private final SettingsService settingsService;
    private final Map<String, MaxBuyPriceStrategy> maxBuyPriceStrategies;

    private static final String DEFAULT_STRATEGY = "lowerFewBidsFromFallenPriceStrategy";

    @Autowired
    public MaxBuyPriceStrategyServiceImpl(SettingsService settingsService,
                                          Map<String, MaxBuyPriceStrategy> maxBuyPriceStrategies) {
        this.settingsService = settingsService;
        this.maxBuyPriceStrategies = maxBuyPriceStrategies;
    }

    @Override
    public MaxBuyPriceStrategy findActiveBuyStrategy() {
        String activeMaxBuyPriceStrategy = settingsService.getSetting(MAX_BUY_PRICE_STRATEGY);
        return maxBuyPriceStrategies.getOrDefault(
                activeMaxBuyPriceStrategy, maxBuyPriceStrategies.get(DEFAULT_STRATEGY)
        );
    }
}
