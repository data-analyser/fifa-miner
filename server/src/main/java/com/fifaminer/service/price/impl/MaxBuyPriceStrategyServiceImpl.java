package com.fifaminer.service.price.impl;

import com.fifaminer.service.price.strategy.MaxBuyPriceStrategy;
import com.fifaminer.service.price.MaxBuyPriceStrategyService;
import com.fifaminer.service.price.strategy.impl.PriceReducingStrategy;
import com.fifaminer.service.setting.SettingsService;
import com.google.common.collect.ImmutableMap.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

import static com.fifaminer.client.dto.strategy.MaxBuyStrategy.*;
import static com.fifaminer.client.dto.strategy.PriceStrategy.MAX_BUY_PRICE;

@Service
public class MaxBuyPriceStrategyServiceImpl implements MaxBuyPriceStrategyService {

    private final SettingsService settingsService;
    private final PriceReducingStrategy priceReducingStrategy;

    private Map<String, MaxBuyPriceStrategy> maxBuyPriceStrategies;

    private static final String DEFAULT_STRATEGY = "reduce20FromCurrentMinStrategy";

    @Autowired
    public MaxBuyPriceStrategyServiceImpl(SettingsService settingsService,
                                          PriceReducingStrategy priceReducingStrategy) {
        this.settingsService = settingsService;
        this.priceReducingStrategy = priceReducingStrategy;
    }

    @PostConstruct
    private void initStrategies() {
        maxBuyPriceStrategies = new Builder<String, MaxBuyPriceStrategy>()
                .put(REDUCE_10_FROM_CURRENT_MIN.getStrategyName(), context -> priceReducingStrategy.reduce(context.getCurrentMin(), 10))
                .put(REDUCE_15_FROM_CURRENT_MIN.getStrategyName(), context -> priceReducingStrategy.reduce(context.getCurrentMin(), 15))
                .put(REDUCE_20_FROM_CURRENT_MIN.getStrategyName(), context -> priceReducingStrategy.reduce(context.getCurrentMin(), 20))
                .put(REDUCE_25_FROM_CURRENT_MIN.getStrategyName(), context -> priceReducingStrategy.reduce(context.getCurrentMin(), 25))
                .put(REDUCE_30_FROM_CURRENT_MIN.getStrategyName(), context -> priceReducingStrategy.reduce(context.getCurrentMin(), 30))
                .build();
    }

    @Override
    public MaxBuyPriceStrategy findActiveBuyStrategy() {
        String activeMaxBuyPriceStrategy = settingsService.getSetting(MAX_BUY_PRICE.name());
        return maxBuyPriceStrategies.getOrDefault(
                activeMaxBuyPriceStrategy, maxBuyPriceStrategies.get(DEFAULT_STRATEGY)
        );
    }
}
