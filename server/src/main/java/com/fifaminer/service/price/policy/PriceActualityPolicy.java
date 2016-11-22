package com.fifaminer.service.price.policy;

import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Map.Entry;

@Slf4j
@Component
public class PriceActualityPolicy {

    private Map<Range<Integer>, Integer> actualityRanges;

    @PostConstruct
    private void initRanges() {
        actualityRanges = new Builder<Range<Integer>, Integer>()
                .put(Range.closedOpen(1, 5), 2)
                .put(Range.closedOpen(5, 12), 3)
                .put(Range.closedOpen(12, 17), 2)
                .put(Range.closed(17, 23), 1)
                .put(Range.closedOpen(0, 1), 1)
                .build();
    }

    public boolean isActualPrices(Long lastDistributionTime) {
        Integer hoursNow = DateTime.now()
                .getHourOfDay();
        Long timeLimit = calculateTimeLimit(findActualityLimit(hoursNow));
        log.info("Last prices distribution time = {}, time limit = {}", lastDistributionTime, timeLimit);
        return lastDistributionTime > timeLimit;
    }

    private Long calculateTimeLimit(Integer limitInHours) {
        return DateTime.now()
                .minusHours(limitInHours)
                .getMillis();
    }

    private Integer findActualityLimit(Integer currentHour) {
        for (Entry<Range<Integer>, Integer> range : actualityRanges.entrySet()) {
            if (range.getKey().contains(currentHour)) return range.getValue();
        }
        throw new IllegalStateException("Cannot find actuality range");
    }
}
