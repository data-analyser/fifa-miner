package com.fifaminer.util;

import java.math.BigDecimal;

public class Percentage {

    private static final BigDecimal MAX_PERCENT = new BigDecimal(100);

    public static Integer from(Integer value, Integer percents) {
        BigDecimal valueToGetPercents = new BigDecimal(value);
        BigDecimal percent = new BigDecimal(percents);
        return percent.divide(MAX_PERCENT)
                .multiply(valueToGetPercents)
                .intValue();
    }
}
