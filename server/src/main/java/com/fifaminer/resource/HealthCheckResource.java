package com.fifaminer.resource;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static com.fifaminer.resource.UrlPath.HEALTH_CHECK;

@Component
@Path(HEALTH_CHECK)
public class HealthCheckResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String healthCheck() {
        return "I am working";
    }
}
