package com.fifaminer.service.price.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerPrice {

    private final Long playerId;
    private final Integer buyPrice;
    private final Integer sellPrice;
    private final Integer profit;
    private final String buyPriceStrategy;
    private final String sellPriceStrategy;
}
