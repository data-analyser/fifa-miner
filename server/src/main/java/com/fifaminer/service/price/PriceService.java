package com.fifaminer.service.price;

import com.fifaminer.service.price.model.PlayerPrice;

public interface PriceService {

    Integer getBuyPrice(Long playerId);

    Integer getSellPrice(Long playerId);

    Integer getBidPrice(Long playerId);

    Integer getProfit(Long playerId);

    PlayerPrice getPlayerPriceInfo(Long playerId);
}
