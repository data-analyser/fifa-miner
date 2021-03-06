package com.fifaminer.service.price;

import com.fifaminer.service.price.model.PlayerPrice;

public interface PriceService {

    Integer getMaxBuyPrice(Long playerId);

    Integer getSellStartPrice(Long playerId);

    Integer getSellBuyNowPrice(Long playerId);

    Integer getBuyNowProfit(Long playerId);

    Integer getStartProfit(Long playerId);

    PlayerPrice getPricesSummary(Long playerId);

    boolean isPriceDistributionActual(Long playerId);
}
