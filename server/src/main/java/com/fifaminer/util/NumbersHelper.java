package com.fifaminer.util;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NumbersHelper {

    public Integer findClosest(Integer value, List<Integer> values) {
        values.sort(Integer::compare);
        return values.contains(value) ? value : getClosestValue(value, values);
    }

    private Integer getClosestValue(Integer value, List<Integer> values) {
        for (int index = 0; index < values.size(); index++) {
            int current = values.get(index);
            int next = values.get(index + 1);
            if (current < value && next > value) {
                return selectClosest(current, value, next);
            }
        }
        throw new IllegalStateException("Cannot find closest value");
    }

    private Integer selectClosest(Integer current,
                                  Integer value,
                                  Integer next) {
        return value - current <= next - value ?
                current : next;
    }
}
