package com.fifaminer.service.price.impl;

import com.fifaminer.service.price.TaxService;
import com.fifaminer.util.Percentage;
import org.springframework.stereotype.Service;

@Service
public class TaxServiceImpl implements TaxService {

    private static final int TAX_IN_PERCENTS = 5;

    @Override
    public Integer calculateTax(Integer amount) {
        return Percentage.from(amount, TAX_IN_PERCENTS);
    }

    @Override
    public Integer reduceTax(Integer amount) {
        return amount - calculateTax(amount);
    }

    @Override
    public Integer addTax(Integer amount) {
        return amount + calculateTax(amount);
    }
}
