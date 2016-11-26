package com.fifaminer.resource;

import com.fifaminer.client.dto.LeagueType;
import com.fifaminer.client.dto.PlayerAttribute;
import com.fifaminer.service.group.PlayerGroupService;
import com.fifaminer.service.group.PlayerSelectionService;
import com.fifaminer.service.marketing.MarketingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Component
@Path("/marketing")
public class MarketingResource {

    private final MarketingService marketingService;

    @Autowired
    public MarketingResource(MarketingService marketingService) {
        this.marketingService = marketingService;
    }

    @GET
    @Path("/prospective-players")
    @Produces(MediaType.APPLICATION_JSON)
    public String finPlayersForMarketing(@QueryParam("leagueType") LeagueType leagueType,
                                         @QueryParam("playerAttribute") PlayerAttribute playerAttribute,
                                         @QueryParam("limit") Integer limit) {
        return marketingService.findProspectivePlayers(leagueType, playerAttribute, limit);
    }

}
