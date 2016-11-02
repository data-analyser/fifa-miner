package com.fifaminer.service.price;

import com.fifaminer.entity.PriceHistory;

public interface PriceHistoryService {

    PriceHistory findByPlayerId(Long playerId);
}
