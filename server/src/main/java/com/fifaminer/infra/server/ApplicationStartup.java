package com.fifaminer.infra.server;

import com.fifaminer.infra.logging.LoggingConfiguration;
import com.fifaminer.infra.server.jetty.JettyServer;

public class ApplicationStartup {

    public static void main(String[] args) {
        new LoggingConfiguration().configure();
        new JettyServer().start();
    }
}
