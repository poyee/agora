package com.poyee.agora.bean;

import com.poyee.agora.entity.User;
import com.poyee.agora.user.LocalUser;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditAwareImpl implements AuditorAware<User> {
    @Override
    public Optional<User> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            LocalUser user = (LocalUser) authentication.getPrincipal();
            return Optional.of(user).map(LocalUser::getUser);
        }

        return Optional.empty();
    }
}
