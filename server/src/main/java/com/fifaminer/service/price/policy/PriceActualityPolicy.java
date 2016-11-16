package com.fifaminer.service.price.policy;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class PriceActualityPolicy {

    private static final int ACTUALITY_LIMIT_IN_HOURS = 3;

    public boolean isActualPrices(Long lastDistributionTime) {
        return lastDistributionTime > getTimeLimit();
    }

    private Long getTimeLimit() {
        return DateTime.now()
                .minusHours(ACTUALITY_LIMIT_IN_HOURS)
                .getMillis();
    }
}
