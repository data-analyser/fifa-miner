package com.fifaminer.resource;

import com.fifaminer.client.dto.Duration;
import com.fifaminer.client.dto.OrderingTypeTO;
import com.fifaminer.service.marketing.PlayerTransactionsService;
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
@Path(PLAYER_TRANSACTIONS)
public class PlayerTransactionsResource {

    private final PlayerTransactionsService playerTransactionsService;

    @Autowired
    public PlayerTransactionsResource(PlayerTransactionsService playerTransactionsService) {
        this.playerTransactionsService = playerTransactionsService;
    }

    @GET
    @Path("/range")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Long> findPlayersByTimeRange(@QueryParam("startTime") Long startTime,
                                             @QueryParam("endTime") Long endTime,
                                             @QueryParam("orderingType") OrderingTypeTO orderingTypeTO,
                                             @QueryParam("groupName") String groupName,
                                             @QueryParam("limit") Integer limit) {
        return playerTransactionsService.findPlayers(
                startTime, endTime, OrderingType.valueOf(orderingTypeTO.name()), groupName, limit
        );
    }

    @GET
    @Path("/duration")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Long> findPlayersByDuration(@QueryParam("duration") Duration duration,
                                            @QueryParam("orderingType") OrderingTypeTO orderingTypeTO,
                                            @QueryParam("groupName") String groupName,
                                            @QueryParam("limit") Integer limit) {
        return playerTransactionsService.findPlayers(
                duration, OrderingType.valueOf(orderingTypeTO.name()), groupName, limit
        );
    }
}
