package com.fifaminer.resource;

import com.fifaminer.client.dto.OrderingTypeTO;
import com.fifaminer.service.marketing.MarketingService;
import com.fifaminer.service.marketing.type.OrderingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static com.fifaminer.resource.UrlPath.*;

@Component
@Path(MARKETING)
public class PlayersMarketingResource {

    private final MarketingService marketingService;

    @Autowired
    public PlayersMarketingResource(MarketingService marketingService) {
        this.marketingService = marketingService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Long> findPlayers(@QueryParam("startTime") Long startTime,
                                  @QueryParam("endTime") Long endTime,
                                  @QueryParam("orderingType") OrderingTypeTO orderingTypeTO,
                                  @QueryParam("limit") Integer limit) {
        return marketingService.findPlayersByTransactionAnalyse(
                startTime, endTime, OrderingType.valueOf(orderingTypeTO.name()), limit
        );
    }
}
