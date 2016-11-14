package com.fifaminer.service.price;

import com.fifaminer.client.dto.Platform;
import com.fifaminer.client.dto.PriceLimits;

public interface PriceLimitService {

    PriceLimits getPriceLimits(Long playerId, Platform platform);
}
