package com.fifaminer.client.impl;

public final class FifaMinerClientBuilder {

    private String protocol;
    private String serverUrl;
    private int port;

    public FifaMinerClientBuilder withProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public FifaMinerClientBuilder withServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
        return this;
    }

    public FifaMinerClientBuilder withPort(int port) {
        this.port = port;
        return this;
    }

    public FifaMinerClientImpl build() {
        return new FifaMinerClientImpl(protocol, this.serverUrl, this.port);
    }
}
