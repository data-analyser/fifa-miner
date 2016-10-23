package com.fifaminer.infra.server;

import com.fifaminer.infra.logging.LoggingConfiguration;
import com.fifaminer.infra.server.jetty.JettyServer;
import com.fifaminer.infra.spring.SpringConfiguration;

public class ApplicationStartup {

    public static void main(String[] args) {
        new SpringConfiguration().configure();
        new LoggingConfiguration().configure();
        new JettyServer().start();
    }
}
