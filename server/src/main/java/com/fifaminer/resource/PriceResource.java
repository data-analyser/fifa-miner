package com.fifaminer.resource;

import com.fifaminer.service.PriceService;
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

    @Autowired
    public PriceResource(PriceService priceService) {
        this.priceService = priceService;
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
}
