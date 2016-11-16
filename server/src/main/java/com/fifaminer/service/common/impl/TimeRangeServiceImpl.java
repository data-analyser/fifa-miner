package com.fifaminer.service.common.impl;

import com.fifaminer.client.dto.Duration;
import com.fifaminer.service.common.TimeRangeService;
import com.fifaminer.service.common.model.TimeRange;
import com.google.common.collect.ImmutableMap.Builder;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.function.Function;

import static com.fifaminer.client.dto.Duration.*;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;

@Service
public class TimeRangeServiceImpl implements TimeRangeService {

    private Map<Duration, Function<Duration, TimeRange>> durationFunctions;

    @PostConstruct
    public void initDurationFunctions() {
        durationFunctions = new Builder<Duration, Function<Duration, TimeRange>>()
                .put(TODAY, todayFunction())
                .put(YESTERDAY, yesterdayFunction())
                .put(LAST_2_DAYS, lastDaysFunction(2))
                .put(LAST_3_DAYS, lastDaysFunction(3))
                .put(LAST_7_DAYS, lastDaysFunction(7))
                .put(LAST_30_DAYS, lastDaysFunction(30))
                .put(THIS_DAY_AT_LAST_WEEK, thisDayDaysAgo(7))
                .put(THIS_DAY_AT_LAST_MONTH, thisDayDaysAgo(30))
                .build();
    }

    @Override
    public TimeRange fromDuration(Duration duration) {
        return durationFunctions.get(duration)
                .apply(duration);
    }

    private Function<Duration, TimeRange> todayFunction() {
        return duration -> new TimeRange(
                DateTime.now()
                        .withTimeAtStartOfDay()
                        .getMillis(),
                DateTime.now().getMillis()
        );
    }

    private Function<Duration, TimeRange> yesterdayFunction() {
        return function -> new TimeRange(
                DateTime.now()
                        .minusDays(INTEGER_ONE)
                        .withTimeAtStartOfDay()
                        .getMillis(),
                DateTime.now()
                        .withTimeAtStartOfDay()
                        .minusMinutes(INTEGER_ONE)
                        .getMillis()
        );
    }

    private Function<Duration, TimeRange> lastDaysFunction(Integer daysCount) {
        return function -> new TimeRange(
                DateTime.now()
                        .minusDays(daysCount)
                        .getMillis(),
                DateTime.now().getMillis()
        );
    }

    private Function<Duration, TimeRange> thisDayDaysAgo(Integer daysCount) {
        return function -> new TimeRange(
                DateTime.now()
                        .minusDays(daysCount)
                        .withTimeAtStartOfDay()
                        .getMillis(),
                DateTime.now()
                        .minusDays(daysCount - INTEGER_ONE)
                        .withTimeAtStartOfDay()
                        .minusMinutes(INTEGER_ONE)
                        .getMillis()
        );
    }
}
