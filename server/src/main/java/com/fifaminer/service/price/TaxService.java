package com.fifaminer.service.price;

public interface TaxService {

    Integer calculateTax(Integer amount);

    Integer reduceTax(Integer amount);

    Integer addTax(Integer amount);
}
