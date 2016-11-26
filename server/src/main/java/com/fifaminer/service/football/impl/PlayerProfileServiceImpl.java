package com.fifaminer.service.football.impl;

import com.fifaminer.entity.PlayerProfile;
import com.fifaminer.repository.PlayerProfileRepository;
import com.fifaminer.service.football.PlayerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerProfileServiceImpl implements PlayerProfileService {

    private final PlayerProfileRepository playerProfileRepository;

    @Autowired
    public PlayerProfileServiceImpl(PlayerProfileRepository playerProfileRepository) {
        this.playerProfileRepository = playerProfileRepository;
    }

    @Override
    public PlayerProfile findById(Long playerId) {
        return playerProfileRepository.findOne(playerId);
    }

    @Override
    public List<PlayerProfile> findByLeagueId(Long leagueId) {
        return playerProfileRepository.findByLeagueId(leagueId);
    }
}
