package com.fifaminer.service.group;

import com.fifaminer.client.dto.LeagueType;
import com.fifaminer.client.dto.PlayerAttribute;

import java.util.List;

public interface PlayerSelectionService {

    List<Long> selectPlayers(LeagueType leagueType, PlayerAttribute sortingAttribute, Integer limit);
}
