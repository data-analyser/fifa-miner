package com.fifaminer.resource;

import com.fifaminer.client.dto.PlayerMarketingTO;
import com.fifaminer.converter.PlayerMarketingConverter;
import com.fifaminer.service.marketing.MarketingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static com.fifaminer.resource.UrlPath.*;
import static java.util.stream.Collectors.toList;

@Component
@Path(MARKETING)
public class PlayersMarketingResource {

    private final MarketingService marketingService;
    private final PlayerMarketingConverter converter;

    @Autowired
    public PlayersMarketingResource(MarketingService marketingService,
                                    PlayerMarketingConverter converter) {
        this.marketingService = marketingService;
        this.converter = converter;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PlayerMarketingTO> findPlayersForMarketing() {
        return marketingService.findPlayersForMarketing().stream()
                .map(converter::toTO)
                .collect(toList());
    }

    @GET
    @Path(MOST_SELLING)
    @Produces(MediaType.APPLICATION_JSON)
    public List<PlayerMarketingTO> findMostSellingPlayers() {
        return marketingService.findMostSellingPlayers().stream()
                .map(converter::toTO)
                .collect(toList());
    }
}
