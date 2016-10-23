package com.fifaminer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * Created by Alexandr on 23.10.2016.
 */
@Component
@ConfigurationProperties(prefix = "dbConnection")
public class DbConnectionConfiguration {

    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String host;
    @NotNull
    private Integer port;
    @NotNull
    private String database;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
