package com.fifaminer.service.common.impl;

import com.fifaminer.service.common.NumbersService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NumbersServiceImpl implements NumbersService {

    @Override
    public Integer findClosest(Integer value, List<Integer> orderedValues) {
        return orderedValues.contains(value) ? value : getClosestValue(value, orderedValues);
    }

    private Integer getClosestValue(Integer value, List<Integer> values) {
        for (int index = 0; index < values.size(); index++) {
            int current = values.get(index);
            int next = values.get(index + 1);
            if (current < value && next > value) {
                return next;
            }
        }
        throw new IllegalStateException("Cannot find closest value");
    }
}
