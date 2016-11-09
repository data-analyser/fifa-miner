package com.fifaminer.service.common;

import java.util.List;

public interface NumbersService {

    Integer findClosest(Integer value, List<Integer> orderedValues);
}