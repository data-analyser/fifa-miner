package com.fifaminer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class PriceSummary {

    private final Long timestamp;
    private final Integer min;
    private final Integer avg;
    private final Integer max;
}
