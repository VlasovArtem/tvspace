package com.vlasovartem.tvspace.config.security;

import com.vlasovartem.tvspace.utils.security.AuthenticatedUserPrincipalUtil;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by artemvlasov on 07/12/15.
 */
public class SpringSecurityAudioAware implements AuditorAware<String> {
    @Override
    public String getCurrentAuditor() {
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return AuthenticatedUserPrincipalUtil.getAuthenticationPrincipal().get().getId();
    }
}
