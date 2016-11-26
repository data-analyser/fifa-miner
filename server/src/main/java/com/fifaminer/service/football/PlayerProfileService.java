package com.fifaminer.service.football;

import com.fifaminer.entity.PlayerProfile;

import java.util.List;

public interface PlayerProfileService {

    PlayerProfile findById(Long playerId);

    List<PlayerProfile> findByLeagueId(Long leagueId);
}
