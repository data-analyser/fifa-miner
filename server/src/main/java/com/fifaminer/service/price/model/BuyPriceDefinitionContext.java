package com.fifaminer.service.price.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class BuyPriceDefinitionContext {

    private final Integer forecastedMin;
    private final Integer currentMin;
}
