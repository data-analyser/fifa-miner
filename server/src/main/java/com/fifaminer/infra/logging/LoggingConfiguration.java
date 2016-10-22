package com.fifaminer.infra.logging;

import org.apache.log4j.PropertyConfigurator;

import java.net.URL;

public class LoggingConfiguration {

    private static final String LOGGING_CONFIGURATION_PATH = "log4j.properties";

    public void configure() {
        PropertyConfigurator.configure(getConfigurationResource());
    }

    private URL getConfigurationResource() {
        return getClass()
                .getClassLoader()
                .getResource(LOGGING_CONFIGURATION_PATH);
    }
}
