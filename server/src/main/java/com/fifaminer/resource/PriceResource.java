package com.fifaminer.resource;

import com.fifaminer.client.dto.PlayerPriceTO;
import com.fifaminer.converter.PlayerPriceConverter;
import com.fifaminer.service.price.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import static com.fifaminer.resource.UrlPath.*;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Component
@Path(PRICES)
public class PriceResource {

    private final PriceService priceService;
    private final PlayerPriceConverter converter;

    @Autowired
    public PriceResource(PriceService priceService,
                         PlayerPriceConverter converter) {
        this.priceService = priceService;
        this.converter = converter;
    }

    @GET
    @Path("{playerId}" + BUY)
    @Produces(APPLICATION_JSON)
    public Integer getBuyPrice(@PathParam("playerId") Long playerId) {
        return priceService.getBuyPrice(playerId);
    }

    @GET
    @Path("{playerId}" + SELL)
    @Produces(APPLICATION_JSON)
    public Integer getSellPrice(@PathParam("playerId") Long playerId) {
        return priceService.getSellPrice(playerId);
    }

    @GET
    @Path("{playerId}" + PROFIT)
    @Produces(APPLICATION_JSON)
    public Integer getProfit(@PathParam("playerId") Long playerId) {
        return priceService.getProfit(playerId);
    }

    @GET
    @Path("{playerId}" + BID)
    @Produces(APPLICATION_JSON)
    public Integer getBidPrice(@PathParam("playerId") Long playerId) {
        return priceService.getBidPrice(playerId);
    }

    @GET
    @Path("{playerId}" + SUMMARY)
    @Produces(APPLICATION_JSON)
    public PlayerPriceTO getPlayerPrice(@PathParam("playerId") Long playerId) {
        return converter.toTO(priceService.getPlayerPriceInfo(playerId));
    }
}
