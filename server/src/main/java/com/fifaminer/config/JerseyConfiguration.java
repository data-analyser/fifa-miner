package com.fifaminer.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfiguration extends ResourceConfig {

    private static final String RESOURCES_PACKAGE = "com.fifaminer.resource";

    public JerseyConfiguration() {
        packages(RESOURCES_PACKAGE);
    }
}
