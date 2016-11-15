package com.fifaminer.service.common.impl;

import com.fifaminer.client.dto.Duration;
import com.fifaminer.service.common.ClockService;
import com.fifaminer.service.common.TimeRangeService;
import com.fifaminer.service.common.model.TimeRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeRangeServiceImpl implements TimeRangeService {

    private final ClockService clockService;

    @Autowired
    public TimeRangeServiceImpl(ClockService clockService) {
        this.clockService = clockService;
    }

    @Override
    public TimeRange fromDuration(Duration duration) {
        return new TimeRange(0L, 0L);
    }
}
