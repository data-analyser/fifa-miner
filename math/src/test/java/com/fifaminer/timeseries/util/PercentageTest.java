package com.fifaminer.timeseries.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class PercentageTest {

    @Test
    public void getPercentFrom() {
        Integer percent = Percentage.from(100, 80);
        assertThat(percent, is(80));
    }

    @Test
    public void getPercentFromMoreValue() {
        Integer percent = Percentage.from(500, 10);
        assertThat(percent, is(50));
    }

    @Test
    public void getPercentFromRound() {
        Integer percent = Percentage.from(36, 20);
        assertThat(percent, is(7));
    }

    @Test
    public void getPercentFromMoreThan100() {
        Integer percent = Percentage.from(55, 200);
        assertThat(percent, is(110));
    }
}