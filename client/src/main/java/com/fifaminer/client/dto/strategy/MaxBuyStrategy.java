package com.fifaminer.client.dto.strategy;

public enum MaxBuyStrategy {

    REDUCE_10_FROM_CURRENT_MIN("reduce10FromCurrentMinStrategy"),
    REDUCE_15_FROM_CURRENT_MIN("reduce15FromCurrentMinStrategy"),
    REDUCE_20_FROM_CURRENT_MIN("reduce20FromCurrentMinStrategy"),
    REDUCE_25_FROM_CURRENT_MIN("reduce25FromCurrentMinStrategy"),
    REDUCE_30_FROM_CURRENT_MIN("reduce30FromCurrentMinStrategy");

    private final String strategyName;

    MaxBuyStrategy(String strategyName) {
        this.strategyName = strategyName;
    }

    public String getStrategyName() {
        return strategyName;
    }
}
