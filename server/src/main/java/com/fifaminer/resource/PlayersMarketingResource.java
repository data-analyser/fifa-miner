package com.fifaminer.resource;

import com.fifaminer.service.marketing.MarketingService;
import com.fifaminer.service.marketing.model.PlayerMarketing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static com.fifaminer.resource.UrlPath.MARKETING;

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
    public List<PlayerMarketing> findPlayersForMarketing() {
        return marketingService.findPlayersForMarketing();
    }
}
