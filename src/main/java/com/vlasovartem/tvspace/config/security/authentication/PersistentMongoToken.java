package com.vlasovartem.tvspace.config.security.authentication;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import java.util.Date;

/**
 * Created by artemvlasov on 07/12/15.
 */
@Document(collection = "persistentlogins")
@CompoundIndexes({
        @CompoundIndex(name = "login", def = "{ 'login' : 1 }"),
        @CompoundIndex(name = "series", def = "{ 'series' : 1 }", unique = true)
})
public class PersistentMongoToken extends PersistentRememberMeToken {

    @Id
    private String id;

    public PersistentMongoToken() {
        this(null, null, null, null);
    }

    public PersistentMongoToken(final PersistentRememberMeToken token) {
        this(token.getUsername(), token.getSeries(), token.getTokenValue(), token.getDate());
    }

    public PersistentMongoToken(final String username, final String series, final String tokenValue, final Date date) {
        super(username, series, tokenValue, date);
    }

    public String getId() {
        return id;
    }
}
