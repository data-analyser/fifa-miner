package com.fifaminer.service.price.impl;

import com.fifaminer.service.price.BidPriceStrategy;
import com.fifaminer.service.price.BidPriceStrategyService;
import com.fifaminer.service.setting.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.fifaminer.service.setting.type.Setting.BID_PRICE_STRATEGY;

@Service
public class BidPriceStrategyServiceImpl implements BidPriceStrategyService {

    private final SettingsService settingsService;
    private final Map<String, BidPriceStrategy> bidPriceStrategies;

    private static final String DEFAULT_STRATEGY = "more10ThanSellPriceStrategy";

    @Autowired
    public BidPriceStrategyServiceImpl(SettingsService settingsService,
                                       Map<String, BidPriceStrategy> bidPriceStrategies) {
        this.settingsService = settingsService;
        this.bidPriceStrategies = bidPriceStrategies;
    }

    @Override
    public BidPriceStrategy findActiveBidStrategy() {
        String activeBidStrategy = settingsService.getSetting(BID_PRICE_STRATEGY);
        return bidPriceStrategies.getOrDefault(
                activeBidStrategy, bidPriceStrategies.get(DEFAULT_STRATEGY)
        );
    }
}
