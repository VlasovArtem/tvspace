package com.vlasovartem.tvspace.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by artemvlasov on 20/11/15.
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.vlasovartem.tvspace.persistence.repository")
@Profile("development")
public class AppConfig extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "heroku_r183c426";
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient(new MongoClientURI("mongodb://heroku_r183c426:qjempqqji3ukn89v4f6icj8k3a@ds059644.mongolab.com:59644/heroku_r183c426"));
    }

    @Override
    protected String getMappingBasePackage() {
        return "com.vlasovartem.tvspace";
    }

}
