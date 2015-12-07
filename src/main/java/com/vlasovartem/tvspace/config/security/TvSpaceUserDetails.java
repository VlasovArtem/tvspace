package com.vlasovartem.tvspace.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Created by artemvlasov on 07/12/15.
 */
public class TvSpaceUserDetails extends User {

    private String id;

    public TvSpaceUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,
                              String id) {
        super(username, password, authorities);
        this.id = id;
    }

    public TvSpaceUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public String getId() {
        return id;
    }
}
