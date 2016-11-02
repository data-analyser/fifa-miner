package com.fifaminer.service.price;

public interface PriceService {

    Integer getBuyPrice(Long playerId);

    Integer getSellPrice(Long playerId);

    Integer getProfit(Long playerId);
}
