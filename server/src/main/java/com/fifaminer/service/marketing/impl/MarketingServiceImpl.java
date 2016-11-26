package com.fifaminer.service.marketing.impl;

import com.fifaminer.client.dto.LeagueType;
import com.fifaminer.client.dto.PlayerAttribute;
import com.fifaminer.service.group.GroupNameGenerator;
import com.fifaminer.service.group.PlayerGroupService;
import com.fifaminer.service.group.PlayerSelectionService;
import com.fifaminer.service.marketing.MarketingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketingServiceImpl implements MarketingService {

    private final PlayerSelectionService playerSelectionService;
    private final GroupNameGenerator groupNameGenerator;
    private final PlayerGroupService playerGroupService;

    @Autowired
    public MarketingServiceImpl(PlayerSelectionService playerSelectionService,
                                GroupNameGenerator groupNameGenerator,
                                PlayerGroupService playerGroupService) {
        this.playerSelectionService = playerSelectionService;
        this.groupNameGenerator = groupNameGenerator;
        this.playerGroupService = playerGroupService;
    }

    @Override
    public String findProspectivePlayers(LeagueType leagueType,
                                         PlayerAttribute playerAttribute,
                                         Integer limit) {
        List<Long> playerIds = playerSelectionService.selectPlayers(
                leagueType, playerAttribute, limit
        );
        String groupName = groupNameGenerator.generate(leagueType, playerAttribute, limit);
        playerGroupService.addPlayersToGroup(playerIds, groupName);
        return groupName;
    }
}
