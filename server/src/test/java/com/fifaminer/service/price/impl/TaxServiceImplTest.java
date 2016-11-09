package com.fifaminer.service.price.impl;

import com.fifaminer.service.price.TaxService;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TaxServiceImplTest {

    private final TaxService taxService = new TaxServiceImpl();

    @Test
    public void testCalculateTax() {
        Integer tax = taxService.calculateTax(100);
        assertThat(tax, is(5));
    }

    @Test
    public void calculateTaxRounding() {
        Integer tax = taxService.calculateTax(350);
        assertThat(tax, is(17));
    }

    @Test
    public void testReduceTax() {
        Integer reducedValue = taxService.reduceTax(100);
        assertThat(reducedValue, is(95));
    }

    @Test
    public void testAddTax() {
        Integer reducedValue = taxService.addTax(100);
        assertThat(reducedValue, is(105));
    }
}