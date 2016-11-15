package com.fifaminer.converter;

import com.fifaminer.client.dto.PlayerPriceTO;
import com.fifaminer.service.price.model.PlayerPrice;
import org.springframework.stereotype.Component;

@Component
public class PlayerPriceConverter {

    public PlayerPriceTO toTO(PlayerPrice playerPrice) {
        return new PlayerPriceTO(
                playerPrice.getPlayerId(),
                playerPrice.getMaxBuyPrice(),
                playerPrice.getSellStartPrice(),
                playerPrice.getSellBuyNowPrice(),
                playerPrice.getProfit(),
                playerPrice.getMaxBuyPriceStrategy(),
                playerPrice.getSellStartPriceStrategy(),
                playerPrice.getSellBuyNowPriceStrategy()
        );
    }
}
