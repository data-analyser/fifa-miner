package com.fifaminer.statistics.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class PriceStatistics {

    private final Long timestamp;
    private final Integer min;
    private final Integer median;
}
