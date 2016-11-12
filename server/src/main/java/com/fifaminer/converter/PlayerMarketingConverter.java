package com.fifaminer.converter;

import com.fifaminer.client.dto.PlayerMarketingTO;
import com.fifaminer.service.marketing.model.PlayerMarketing;
import org.springframework.stereotype.Component;

@Component
public class PlayerMarketingConverter {

    public PlayerMarketingTO toTO(PlayerMarketing playerMarketing) {
        return new PlayerMarketingTO(
                playerMarketing.getPlayerId(),
                playerMarketing.getBuyPrice(),
                playerMarketing.getSellPrice()
        );
    }
}
