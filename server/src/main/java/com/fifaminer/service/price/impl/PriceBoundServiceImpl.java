package com.fifaminer.service.price.impl;

import com.fifaminer.service.common.NumbersService;
import com.fifaminer.service.price.PriceBoundService;
import com.fifaminer.service.price.type.BoundSelection;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.math.NumberUtils.*;

@Service
public class PriceBoundServiceImpl implements PriceBoundService {

    private final NumbersService numbersService;

    private Map<Range<Integer>, List<Integer>> bounds;

    private static final int MIN_BID = 150;
    private static final int MAX_BID = 100_000;

    @Autowired
    public PriceBoundServiceImpl(NumbersService numbersService) {
        this.numbersService = numbersService;
    }

    @PostConstruct
    public void initBounds() {
        bounds = new Builder<Range<Integer>, List<Integer>>()
                .put(Range.openClosed(150, 1000), generateBounds(150, 1000, 50))
                .put(Range.openClosed(1000, 10_000), generateBounds(1000, 10_000, 100))
                .put(Range.openClosed(10_000, 50_000), generateBounds(10_000, 50_000, 250))
                .put(Range.openClosed(50_000, 100_000), generateBounds(50_000, 100_000, 500))
                .build();
    }

    @Override
    public Integer arrangeToBound(Integer amount, BoundSelection boundSelection) {
        Preconditions.checkArgument(amount <= MAX_BID, "Cannot configure bound");
        return amount < MIN_BID ? MIN_BID : applyBounds(amount, boundSelection);
    }

    private List<Integer> generateBounds(Integer lower, Integer upper, Integer step) {
        return IntStream.iterate(lower, i -> i + step)
                .limit(((upper - lower) / step) + INTEGER_ONE)
                .boxed()
                .collect(toList());
    }

    private Integer applyBounds(Integer amount, BoundSelection boundSelection) {
        for (Entry<Range<Integer>, List<Integer>> bound : bounds.entrySet()) {
            if (bound.getKey().contains(amount)) {
                return numbersService.findClosest(amount, bound.getValue(), boundSelection);
            }
        }
        return amount;
    }
}
