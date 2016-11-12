package com.fifaminer.service.price;

import com.fifaminer.service.price.type.BoundSelection;

public interface PriceBoundService {

    Integer arrangeToBound(Integer amount, BoundSelection boundSelection);
}
