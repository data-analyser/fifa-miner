package com.fifaminer.timeseries.util;

import com.fifaminer.util.Percentage;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class PercentageTest {

    @Test
    public void testGetPercentFrom() {
        Integer percent = Percentage.from(100, 80);
        assertThat(percent, is(80));
    }

    @Test
    public void testGetPercentFromMoreValue() {
        Integer percent = Percentage.from(500, 10);
        assertThat(percent, is(50));
    }

    @Test
    public void testGetPercentFromRound() {
        Integer percent = Percentage.from(36, 20);
        assertThat(percent, is(7));
    }

    @Test
    public void testGetPercentFromMoreThan100() {
        Integer percent = Percentage.from(55, 200);
        assertThat(percent, is(110));
    }

    @Test
    public void testOfTotalFrom50() {
        Integer percent = Percentage.ofTotal(100, 50);
        assertThat(percent, is(50));
    }

    @Test
    public void testOfTotalFrom33() {
        Integer percent = Percentage.ofTotal(100, 33);
        assertThat(percent, is(33));
    }

    @Test
    public void testOfTotalMoreThan100() {
        Integer percent = Percentage.ofTotal(50, 200);
        assertThat(percent, is(400));
    }

    @Test
    public void testOfTotalRounded() {
        Integer percent = Percentage.ofTotal(3, 1);
        assertThat(percent, is(34));
    }
}