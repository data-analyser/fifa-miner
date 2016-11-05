package com.fifaminer.service.marketing.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerMarketing {

    private final Long playerId;
    private final Integer buyPrice;
    private final Integer sellPrice;
}
