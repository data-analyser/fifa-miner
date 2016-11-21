package com.fifaminer.client.dto.strategy;

public enum SellStartStrategy {

    LOWER_FEW_BIDS_FROM_BUY_NOW_PRICE("lowerFewBidsFromBuyNowPriceStrategy");

    private final String strategyName;

    SellStartStrategy(String strategyName) {
        this.strategyName = strategyName;
    }

    public String getStrategyName() {
        return strategyName;
    }
}
