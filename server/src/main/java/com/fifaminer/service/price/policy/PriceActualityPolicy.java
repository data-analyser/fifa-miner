package com.fifaminer.service.price.policy;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PriceActualityPolicy {

    private static final int ACTUALITY_LIMIT_IN_HOURS = 3;

    public boolean isActualPrices(Long lastDistributionTime) {
        Long timeLimit = calculateTimeLimit();
        log.info("Last prices distribution time = {}, time limit = {}", lastDistributionTime, timeLimit);
        return lastDistributionTime > timeLimit;
    }

    private Long calculateTimeLimit() {
        return DateTime.now()
                .minusHours(ACTUALITY_LIMIT_IN_HOURS)
                .getMillis();
    }
}
