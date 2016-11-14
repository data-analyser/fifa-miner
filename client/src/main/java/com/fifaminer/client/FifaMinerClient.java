package com.fifaminer.client;

import com.fifaminer.client.dto.*;

import java.util.List;

public interface FifaMinerClient {

    void runForceTransactionAnalyse();

    Integer getBuyPrice(Long playerId);

    Integer getSellPrice(Long playerId);

    Integer getBidPrice(Long playerId);

    Integer getProfit(Long playerId);

    String getSetting(SettingTO settingTO);

    void updateSetting(SettingConfigurationTO settingConfigurationTO);

    PlayerPriceTO getPriceSummary(Long playerId);

    List<PlayerPriceTO> findPlayersByTransactionsAnalyse(Long startTime, Long endTime, OrderingTypeTO orderingTypeTO, Integer limit);

    PriceLimits getPriceLimits(Long playerId, Platform platform);
}

