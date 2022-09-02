package com.poyee.agora.user;

import com.poyee.agora.bean.SignUpRequest;
import com.poyee.agora.entity.User;
import com.poyee.agora.exception.auth.UserAlreadyExistAuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import java.util.Map;
import java.util.Optional;

/**
 * @author Chinna
 * @since 26/3/18
 */
public interface UserService {
	User registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException;

	User findByEmail(String email);

	LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo);

	Optional<User> findById(Long id);
}
