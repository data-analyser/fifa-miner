package com.fifaminer.service.price.impl;

import com.fifaminer.service.price.BuyPriceStrategy;
import com.fifaminer.service.price.BuyPriceStrategyService;
import com.fifaminer.service.setting.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.fifaminer.service.setting.type.Setting.BUY_PRICE_STRATEGY;

@Service
public class BuyPriceStrategyServiceImpl implements BuyPriceStrategyService {

    private final SettingsService settingsService;
    private final Map<String, BuyPriceStrategy> buyPriceStrategies;

    private static final String DEFAULT_STRATEGY = "lowestFromMinimalsStrategy";

    @Autowired
    public BuyPriceStrategyServiceImpl(SettingsService settingsService,
                                       Map<String, BuyPriceStrategy> buyPriceStrategies) {
        this.settingsService = settingsService;
        this.buyPriceStrategies = buyPriceStrategies;
    }

    @Override
    public BuyPriceStrategy findActiveBuyStrategy() {
        String activeBuyPriceStrategy = settingsService.getSetting(BUY_PRICE_STRATEGY);
        return buyPriceStrategies.getOrDefault(
                activeBuyPriceStrategy, buyPriceStrategies.get(DEFAULT_STRATEGY)
        );
    }
}
