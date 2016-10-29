package com.fifaminer.service;

public interface PriceService {

    Integer getBuyPrice(Long playerId);

    Integer getSellPrice(Long playerId);
}
