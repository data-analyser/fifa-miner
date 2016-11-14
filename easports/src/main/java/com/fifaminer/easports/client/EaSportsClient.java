package com.fifaminer.easports.client;

import com.fifaminer.easports.model.PlayerPriceLimits;

public interface EaSportsClient {

    PlayerPriceLimits getPriceLimits(Long playerId);
}
