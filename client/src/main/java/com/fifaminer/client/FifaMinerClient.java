package com.fifaminer.client;

import com.fifaminer.client.dto.PlayerMarketingTO;
import com.fifaminer.client.dto.SettingConfigurationTO;
import com.fifaminer.client.dto.SettingTO;

import java.util.List;

public interface FifaMinerClient {

    void runForceTransactionAnalyse();

    Integer getBuyPrice(Long playerId);

    Integer getSellPrice(Long playerId);

    Integer getProfit(Long playerId);

    String getSetting(SettingTO settingTO);

    void updateSetting(SettingConfigurationTO settingConfigurationTO);

    List<PlayerMarketingTO> getPlayersWithLowestRelists();

    List<PlayerMarketingTO> getMostSellingPlayers();
}

