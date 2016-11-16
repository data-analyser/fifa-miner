package com.fifaminer.resource;

import com.fifaminer.client.dto.Platform;
import com.fifaminer.client.dto.PlayerPriceTO;
import com.fifaminer.client.dto.PriceLimits;
import com.fifaminer.converter.PlayerPriceConverter;
import com.fifaminer.service.price.PriceLimitService;
import com.fifaminer.service.price.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;

import static com.fifaminer.resource.UrlPath.*;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Component
@Path(PRICES)
public class PriceResource {

    private final PriceService priceService;
    private final PriceLimitService priceLimitService;
    private final PlayerPriceConverter converter;

    @Autowired
    public PriceResource(PriceService priceService,
                         PriceLimitService priceLimitService,
                         PlayerPriceConverter converter) {
        this.priceService = priceService;
        this.priceLimitService = priceLimitService;
        this.converter = converter;
    }

    @GET
    @Path("{playerId}" + MAX_BUY)
    @Produces(APPLICATION_JSON)
    public Integer getMaxBuyPrice(@PathParam("playerId") Long playerId) {
        return priceService.getMaxBuyPrice(playerId);
    }

    @GET
    @Path("{playerId}" + SELL_START)
    @Produces(APPLICATION_JSON)
    public Integer getSellStartPrice(@PathParam("playerId") Long playerId) {
        return priceService.getSellStartPrice(playerId);
    }

    @GET
    @Path("{playerId}" + PROFIT)
    @Produces(APPLICATION_JSON)
    public Integer getProfit(@PathParam("playerId") Long playerId) {
        return priceService.getProfit(playerId);
    }

    @GET
    @Path("{playerId}" + SELL_BUY_NOW)
    @Produces(APPLICATION_JSON)
    public Integer getSellBuyNowPrice(@PathParam("playerId") Long playerId) {
        return priceService.getSellBuyNowPrice(playerId);
    }

    @GET
    @Path("{playerId}" + SUMMARY)
    @Produces(APPLICATION_JSON)
    public PlayerPriceTO getPricesSummary(@PathParam("playerId") Long playerId) {
        return converter.toTO(priceService.getPricesSummary(playerId));
    }

    @GET
    @Path("{playerId}" + LIMITS)
    @Produces(APPLICATION_JSON)
    public PriceLimits getPriceLimits(@PathParam("playerId") Long playerId,
                                      @QueryParam("platform") Platform platform) {
        return priceLimitService.getPriceLimits(playerId, platform);
    }

    @GET
    @Path("{playerId}" + IS_ACTUAL)
    @Produces(APPLICATION_JSON)
    public Boolean isPriceDistributionActual(@PathParam("playerId") Long playerId) {
        return priceService.isPriceDistributionActual(playerId);
    }
}
