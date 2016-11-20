package com.fifaminer.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static com.fifaminer.resource.UrlPath.HEALTH_CHECK;

@Component
@Path(HEALTH_CHECK)
@Slf4j
public class HealthCheckResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String healthCheck() {
        log.info("Check healthy");
        return "I am working";
    }
}
