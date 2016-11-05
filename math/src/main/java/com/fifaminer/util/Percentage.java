package com.fifaminer.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.BigDecimal.valueOf;

public class Percentage {

    private static final BigDecimal MAX_PERCENT = new BigDecimal(100);

    public static Integer from(Integer value, Integer percents) {
        return valueOf(percents).divide(MAX_PERCENT)
                .multiply(valueOf(value))
                .intValue();
    }

    public static Integer ofTotal(Integer total, Integer value) {
        return valueOf(value).multiply(MAX_PERCENT)
                .divide(valueOf(total), RoundingMode.CEILING)
                .intValue();
    }
}
