package com.fifaminer.resource;

import com.fifaminer.client.dto.OrderingTypeTO;
import com.fifaminer.client.dto.PlayerPriceTO;
import com.fifaminer.converter.PlayerPriceConverter;
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
import static java.util.stream.Collectors.toList;

@Component
@Path(MARKETING)
public class PlayersMarketingResource {

    private final MarketingService marketingService;
    private final PlayerPriceConverter converter;

    @Autowired
    public PlayersMarketingResource(MarketingService marketingService,
                                    PlayerPriceConverter converter) {
        this.marketingService = marketingService;
        this.converter = converter;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PlayerPriceTO> findPlayers(@QueryParam("startTime") Long startTime,
                                           @QueryParam("endTime") Long endTime,
                                           @QueryParam("orderingType") OrderingTypeTO orderingTypeTO,
                                           @QueryParam("limit") Integer limit) {
        OrderingType orderingType = OrderingType.valueOf(orderingTypeTO.name());
        return marketingService.findPlayersByTransactionAnalyse(startTime, endTime, orderingType, limit)
                .stream()
                .map(converter::toTO)
                .collect(toList());
    }
}
