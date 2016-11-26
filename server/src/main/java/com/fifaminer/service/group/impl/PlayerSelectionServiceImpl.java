package com.fifaminer.service.group.impl;

import com.fifaminer.entity.League;
import com.fifaminer.entity.PlayerProfile;
import com.fifaminer.service.football.LeagueService;
import com.fifaminer.service.football.PlayerProfileService;
import com.fifaminer.service.group.AttributeExtractor;
import com.fifaminer.service.group.PlayerSelectionService;
import com.fifaminer.client.dto.LeagueType;
import com.fifaminer.client.dto.PlayerAttribute;
import org.spark_project.guava.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.fifaminer.client.dto.PlayerAttribute.PACE;
import static java.lang.Double.compare;
import static java.util.stream.Collectors.toList;

@Service
public class PlayerSelectionServiceImpl implements PlayerSelectionService {

    private final LeagueService leagueService;
    private final PlayerProfileService playerProfileService;
    private final AttributeExtractor attributeExtractor;

    private Map<PlayerAttribute, Comparator<PlayerProfile>> sortingComparators;

    @PostConstruct
    private void initSorters() {
        sortingComparators = ImmutableMap.of(
                PACE, (current, next) -> compare(attributeExtractor.getAttribute(current, PACE), attributeExtractor.getAttribute(next, PACE))
        );
    }

    @Autowired
    public PlayerSelectionServiceImpl(LeagueService leagueService,
                                      PlayerProfileService playerProfileService,
                                      AttributeExtractor attributeExtractor) {
        this.leagueService = leagueService;
        this.playerProfileService = playerProfileService;
        this.attributeExtractor = attributeExtractor;
    }

    @Override
    public List<Long> selectPlayers(LeagueType leagueType, PlayerAttribute sortingAttribute, Integer limit) {
        League league = leagueService.findByName(leagueType.getName());
        return playerProfileService.findByLeagueId(league.getId())
                .stream()
                .sorted(byAttribute(sortingAttribute).reversed())
                .limit(limit)
                .sorted(byRating().reversed())
                .map(PlayerProfile::getId)
                .collect(toList());
    }

    private Comparator<PlayerProfile> byRating() {
        return (current, next) -> Integer.compare(current.getRating(), next.getRating());
    }

    private Comparator<PlayerProfile> byAttribute(PlayerAttribute sortingAttribute) {
        return sortingComparators.get(sortingAttribute);
    }
}
