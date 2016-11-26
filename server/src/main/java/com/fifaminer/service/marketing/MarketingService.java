package com.fifaminer.service.marketing;

import com.fifaminer.client.dto.LeagueType;
import com.fifaminer.client.dto.PlayerAttribute;

public interface MarketingService {

    String findProspectivePlayers(LeagueType leagueType, PlayerAttribute playerAttribute, Integer limit);
}
