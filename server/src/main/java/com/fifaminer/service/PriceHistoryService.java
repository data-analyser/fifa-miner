package com.fifaminer.service;

import com.fifaminer.entity.PriceHistory;

public interface PriceHistoryService {

    PriceHistory findByPlayerId(Long playerId);
}
