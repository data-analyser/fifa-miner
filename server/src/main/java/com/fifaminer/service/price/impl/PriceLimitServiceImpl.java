package com.fifaminer.service.price.impl;

import com.fifaminer.client.dto.Platform;
import com.fifaminer.client.dto.PriceLimits;
import com.fifaminer.easports.client.EaSportsClient;
import com.fifaminer.easports.model.Limits;
import com.fifaminer.service.price.PriceLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceLimitServiceImpl implements PriceLimitService {

    private final EaSportsClient eaSportsClient;
    private final LimitsExtractFunctionFactory limitsExtractionFactory;

    @Autowired
    public PriceLimitServiceImpl(EaSportsClient eaSportsClient,
                                 LimitsExtractFunctionFactory limitsExtractionFactory) {
        this.eaSportsClient = eaSportsClient;
        this.limitsExtractionFactory = limitsExtractionFactory;
    }

    @Override
    public PriceLimits getPriceLimits(Long playerId, Platform platform) {
        Limits platformLimits = limitsExtractionFactory.getExtractFunction(platform)
                .apply(eaSportsClient.getPriceLimits(playerId));
        return new PriceLimits(
                playerId,
                platformLimits.getMinPrice(),
                platformLimits.getMaxPrice()
        );
    }
}
