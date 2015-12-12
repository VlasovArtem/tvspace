package com.vlasovartem.tvspace.config.security;

import com.vlasovartem.tvspace.entity.User;
import com.vlasovartem.tvspace.entity.enums.UserRole;
import com.vlasovartem.tvspace.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.jws.soap.SOAPBinding;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by artemvlasov on 07/12/15.
 */
@Component
public class TvSpaceUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    private final int PROJECT_ROLES_SIZE = UserRole.values().length;

    @Override
    public UserDetails loadUserByUsername(String loginData) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepository.loginUser(loginData));
        Set<GrantedAuthority> authorities = new HashSet<>(PROJECT_ROLES_SIZE);
        if(user.isPresent()) {
            if(UserRole.ADMIN.equals(user.get().getRole())) {
                authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.name()));
            }
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.name()));
        } else {
            throw new UsernameNotFoundException("User with login data is not exists");
        }
        return new TvSpaceUserDetails(user.get().getUsername(), user.get().getPassword(), authorities, user.get().getId());
    }
}
