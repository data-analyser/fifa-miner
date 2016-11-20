package com.fifaminer.service.price.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class DeviationPriceDistribution {

    private final Integer deviation;
    private final PricesDistribution pricesDistribution;
}
