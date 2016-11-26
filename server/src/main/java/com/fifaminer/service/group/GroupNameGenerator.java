package com.fifaminer.service.group;

import com.fifaminer.client.dto.LeagueType;
import com.fifaminer.client.dto.PlayerAttribute;
import com.google.common.base.Joiner;
import org.springframework.stereotype.Component;

@Component
public class GroupNameGenerator {

    private static final String SEPARATOR = "/";

    public String generate(LeagueType leagueType,
                           PlayerAttribute playerAttribute,
                           Integer limit) {
        return Joiner.on(SEPARATOR)
                .join(leagueType, playerAttribute, limit);
    }
}
