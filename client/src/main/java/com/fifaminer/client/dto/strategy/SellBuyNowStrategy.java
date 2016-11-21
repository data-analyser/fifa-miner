package com.fifaminer.client.dto.strategy;

public enum SellBuyNowStrategy {

    ONE_BID_LESS_THAN_FIRST_MAXIMUM("oneBidLessThanFirstMaximumStrategy");

    private final String strategyName;

    SellBuyNowStrategy(String strategyName) {
        this.strategyName = strategyName;
    }

    public String getStrategyName() {
        return strategyName;
    }
}
