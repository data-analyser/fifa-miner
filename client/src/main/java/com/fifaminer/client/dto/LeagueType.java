package com.fifaminer.client.dto;

import static java.util.Arrays.stream;

public enum LeagueType {

    PREMIER_LEAGUE("Premier League"),
    CALCIO_A("Calcio A"),
    LALIGA_SANTANDER("LaLiga Santander"),
    BUNDESLIGA("Bundesliga");

    private final String name;

    LeagueType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static LeagueType fromName(String leagueName) {
        return stream(values())
                .filter(league -> league.getName().equals(leagueName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Unsupported league"));
    }
}
