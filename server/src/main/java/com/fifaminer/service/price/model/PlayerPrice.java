package com.fifaminer.service.price.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerPrice {

    private final Long playerId;
    private final Integer maxBuyPrice;
    private final Integer sellStartPrice;
    private final Integer sellBuyNowPrice;
    private final Integer startProfit;
    private final Integer buyNowProfit;
    private final String maxBuyPriceStrategy;
    private final String sellStartPriceStrategy;
    private final String sellBuyNowPriceStrategy;
}
