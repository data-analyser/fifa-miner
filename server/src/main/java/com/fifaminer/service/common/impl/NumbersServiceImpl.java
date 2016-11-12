package com.fifaminer.service.common.impl;

import com.fifaminer.service.common.NumbersService;
import com.fifaminer.service.price.type.BoundSelection;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.fifaminer.service.price.type.BoundSelection.HIGHER;

@Service
public class NumbersServiceImpl implements NumbersService {

    @Override
    public Integer findClosest(Integer value, List<Integer> orderedValues, BoundSelection boundSelection) {
        return orderedValues.contains(value) ? value : getClosestValue(value, orderedValues, boundSelection);
    }

    private Integer getClosestValue(Integer value, List<Integer> orderedValues,
                                    BoundSelection boundSelection) {
        for (int i = 0; i < orderedValues.size(); i++) {
            int current = orderedValues.get(i);
            int next = orderedValues.get(i + 1);
            if (current < value && next > value) {
                return selectValue(current, next, boundSelection);
            }
        }
        throw new IllegalStateException("Cannot find closest value");
    }

    private Integer selectValue(Integer current,
                                Integer next,
                                BoundSelection boundSelection) {
        return boundSelection.equals(HIGHER) ? next : current;
    }
}
