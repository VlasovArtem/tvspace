package com.vlasovartem.tvspace.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by artemvlasov on 10/12/15.
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.vlasovartem.tvspace.persistence.repository")
@Profile("test")
public class TestAppConfig extends AbstractMongoConfiguration {
    @Override
    protected String getDatabaseName() {
        return "testpmdb";
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient(new MongoClientURI("mongodb://localhost"));
    }

    @Override
    protected String getMappingBasePackage() {
        return "com.vlasovartem.tvspace";
    }
}
