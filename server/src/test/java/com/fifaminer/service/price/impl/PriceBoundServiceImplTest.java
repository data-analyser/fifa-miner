package com.fifaminer.service.price.impl;

import com.fifaminer.service.common.impl.NumbersServiceImpl;
import com.fifaminer.service.price.PriceBoundService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PriceBoundServiceImplTest {

    private final PriceBoundServiceImpl priceBoundService = new PriceBoundServiceImpl(new NumbersServiceImpl());

    @Before
    public void setUp() {
        priceBoundService.initBounds();
    }

    @Test
    public void testArrangeToBound() {
        priceBoundService.arrangeToBound(220);
    }
}