package com.fifaminer.service.impl;

import com.fifaminer.entity.PriceHistory;
import com.fifaminer.service.PriceHistoryService;
import com.fifaminer.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceServiceIml implements PriceService {

    private final PriceHistoryService priceHistoryService;

    @Autowired
    public PriceServiceIml(PriceHistoryService priceHistoryService) {
        this.priceHistoryService = priceHistoryService;
    }

    @Override
    public Integer getBuyPrice(Long playerId) {
        PriceHistory playerPriceHistory = priceHistoryService.findByPlayerId(playerId);
        return 1;
    }

    @Override
    public Integer getSellPrice(Long playerId) {
        return 1;
    }
}
