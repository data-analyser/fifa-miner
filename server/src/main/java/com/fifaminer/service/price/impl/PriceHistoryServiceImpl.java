package com.fifaminer.service.price.impl;

import com.fifaminer.entity.PriceHistory;
import com.fifaminer.repository.PriceHistoryRepository;
import com.fifaminer.service.price.PriceHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceHistoryServiceImpl implements PriceHistoryService {

    private final PriceHistoryRepository priceHistoryRepository;

    @Autowired
    public PriceHistoryServiceImpl(PriceHistoryRepository priceHistoryRepository) {
        this.priceHistoryRepository = priceHistoryRepository;
    }

    @Override
    public PriceHistory findByPlayerId(Long playerId) {
        return priceHistoryRepository.findOne(playerId);
    }
}
