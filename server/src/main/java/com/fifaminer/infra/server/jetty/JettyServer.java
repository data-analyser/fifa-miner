package com.fifaminer.infra.server.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JettyServer {

    private static final String SERVER_HOST = "localhost";
    private static final int PORT = 8080;

    private static final Logger logger = LoggerFactory.getLogger(JettyServer.class);

    public void start() {
        try {
            createServer().start();
        } catch (Exception e) {
            logger.error("Cannot start embedded Jetty server", e);
        }
    }

    private Server createServer() {
        Server server = new Server();
        server.addConnector(createConnector(server));
        return server;
    }

    private ServerConnector createConnector(Server server) {
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(PORT);
        connector.setHost(SERVER_HOST);
        return connector;
    }
}
