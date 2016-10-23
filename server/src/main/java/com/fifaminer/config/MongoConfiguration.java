package com.fifaminer.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;


import static com.google.common.collect.ImmutableList.of;
import static com.mongodb.MongoCredential.createMongoCRCredential;

/**
 * Created by Alexandr on 23.10.2016.
 */
@Configuration
public class MongoConfiguration {

    private final DbConnectionConfiguration dbConfiguration;

    private static final String ADMIN_DATABASE = "admin";

    @Autowired
    public MongoConfiguration(DbConnectionConfiguration dbConfiguration) {
        this.dbConfiguration = dbConfiguration;
    }

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory(createMongoClient(), dbConfiguration.getDatabase());
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }

    private MongoClient createMongoClient() {
        return new MongoClient(createServerAddress(), of(createMongoCredential()));
    }

    private ServerAddress createServerAddress() {
        return new ServerAddress(
                dbConfiguration.getHost(),
                dbConfiguration.getPort()
        );
    }

    private MongoCredential createMongoCredential() {
        return createMongoCRCredential(
                dbConfiguration.getUsername(),
                ADMIN_DATABASE,
                dbConfiguration.getPassword().toCharArray()
        );
    }
}
