package com.vlasovartem.tvspace.config.security.authentication;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import java.util.Date;

/**
 * Created by artemvlasov on 07/12/15.
 */
public class MongoTokenRepositoryImpl implements PersistentTokenRepository {

    private final MongoOperations mongo;

    public MongoTokenRepositoryImpl(final MongoOperations mongo) {
        this.mongo = mongo;
    }

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        final PersistentMongoToken mongoToken = new PersistentMongoToken(token);
        mongo.insert(mongoToken);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        final Query query = Query.query(Criteria.where("series").is(series));
        final Update update = Update.update("tokenValue", tokenValue).set("lastUsed", lastUsed);
        mongo.updateFirst(query, update, PersistentMongoToken.class);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String series) {
        final Query query = Query.query(Criteria.where("series").is(series));
        return mongo.findOne(query, PersistentMongoToken.class);
    }

    @Override
    public void removeUserTokens(String username) {
        final Query query = Query.query(Criteria.where("login").is(username));
        mongo.remove(query, PersistentMongoToken.class);
    }
}
