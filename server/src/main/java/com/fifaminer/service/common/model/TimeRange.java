package com.fifaminer.service.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class TimeRange {

    private final Long startTime;
    private final Long endTime;
}
