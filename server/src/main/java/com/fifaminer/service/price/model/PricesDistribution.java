package com.fifaminer.service.price.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class PricesDistribution {

    private final Integer price;
    private final Integer sellers;
}
