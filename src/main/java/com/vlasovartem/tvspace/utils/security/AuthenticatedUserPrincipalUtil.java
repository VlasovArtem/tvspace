package com.vlasovartem.tvspace.utils.security;

import com.vlasovartem.tvspace.config.security.TvSpaceUserDetails;
import com.vlasovartem.tvspace.entity.enums.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by artemvlasov on 07/12/15.
 */
public class AuthenticatedUserPrincipalUtil {
    public static Optional<TvSpaceUserDetails> getAuthenticationPrincipal() {
        if(SecurityContextHolder.getContext() != null) {
            Optional<Authentication> authentication = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
            if(authentication.isPresent()) {
                if(authentication.get().isAuthenticated()) {
                    if(authentication.get().getPrincipal() instanceof TvSpaceUserDetails) {
                        return Optional.of((TvSpaceUserDetails) authentication.get().getPrincipal());
                    }
                }
            }
        }
        return Optional.empty();
    }
    public static boolean containAuthorities(UserRole... roles) {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream()
                .anyMatch(p -> Arrays.asList(roles).contains(UserRole.valueOf(p.getAuthority())));
    }
}
