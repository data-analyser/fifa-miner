package com.fifaminer.service.football.impl;

import com.fifaminer.entity.League;
import com.fifaminer.repository.LeagueRepository;
import com.fifaminer.service.football.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeagueServiceImpl implements LeagueService {

    private final LeagueRepository leagueRepository;

    @Autowired
    public LeagueServiceImpl(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    @Override
    public League findByName(String leagueName) {
        return leagueRepository.findByName(leagueName);
    }
}
