package com.vlasovartem.tvspace.utils.security;

import com.vlasovartem.tvspace.config.security.TvSpaceUserDetails;
import com.vlasovartem.tvspace.entity.enums.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by artemvlasov on 07/12/15.
 */
public class AuthenticatedUserPrincipalUtil {
    public static Optional<TvSpaceUserDetails> getAuthenticationPrincipal() {
        Optional<Authentication> authentication = getAuthentication();
        if(authentication.isPresent()) {
            if(authentication.get().isAuthenticated()) {
                if(authentication.get().getPrincipal() instanceof TvSpaceUserDetails) {
                    return Optional.of((TvSpaceUserDetails) authentication.get().getPrincipal());
                }
            }
        }
        return Optional.empty();
    }
    public static boolean containAuthorities(UserRole... roles) {
        Optional<Authentication> authentication = getAuthentication();
        return authentication.isPresent() && authentication.get().getAuthorities().stream().anyMatch(p ->
                !"ROLE_ANONYMOUS".equals(p.getAuthority())
                        && Arrays.asList(roles).contains(UserRole.valueOf(p.getAuthority())));
    }

    public static String getAuthenticationId () {
        Optional<TvSpaceUserDetails> userDetails = getAuthenticationPrincipal();
        if(userDetails.isPresent()) {
            return userDetails.get().getId();
        }
        return null;
    }

    public static boolean isAuthenticated() {
        Optional<Authentication> authentication = getAuthentication();
        return authentication.isPresent()
                && authentication.get().isAuthenticated()
                && containAuthorities(UserRole.ADMIN, UserRole.USER);
    }

    private static Optional<Authentication> getAuthentication() {
        if(SecurityContextHolder.getContext() != null) {
            return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
        }
        return Optional.empty();
    }
}
