package com.fifaminer.service.common;

import com.fifaminer.client.dto.Duration;
import com.fifaminer.service.common.model.TimeRange;

public interface TimeRangeService {

    TimeRange fromDuration(Duration duration);
}
