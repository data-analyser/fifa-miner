package com.fifaminer.service.marketing;

import com.fifaminer.service.marketing.model.PlayerMarketing;

import java.util.List;

public interface MarketingService {

    List<PlayerMarketing> findPlayersForMarketing();

    List<PlayerMarketing> findMostSellingPlayers();
}
