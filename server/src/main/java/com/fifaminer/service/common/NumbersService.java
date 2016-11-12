package com.fifaminer.service.common;

import com.fifaminer.service.price.type.BoundSelection;

import java.util.List;

public interface NumbersService {

    Integer findClosest(Integer value, List<Integer> orderedValues, BoundSelection boundSelection);
}