package com.poyee.agora.user;

import com.poyee.agora.bean.SignUpRequest;
import com.poyee.agora.entity.Role;
import com.poyee.agora.entity.User;
import com.poyee.agora.exception.auth.OAuth2AuthenticationProcessingException;
import com.poyee.agora.exception.auth.UserAlreadyExistAuthenticationException;
import com.poyee.agora.role.RoleRepository;
import com.poyee.agora.security.oauth2.user.OAuth2UserInfo;
import com.poyee.agora.security.oauth2.user.OAuth2UserInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

/**
 * @author Chinna
 * @since 26/3/18
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	@Transactional(value = "transactionManager")
	public User registerNewUser(final SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException {
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new UserAlreadyExistAuthenticationException("User with email id " + signUpRequest.getEmail() + " already exist");
		}

		User user = buildUser(signUpRequest);

		user = userRepository.save(user);
		userRepository.flush();
		return user;
	}

	private User buildUser(final SignUpRequest request) {
		User user = new User();
		user.setEmail(request.getEmail());
		user.setDisplayName(request.getDisplayName());
		final HashSet<Role> roles = new HashSet<>();
		roles.add(roleRepository.findByName(Role.ROLE_USER));
		user.setRoles(roles);
		return user;
	}

	@Override
	@Transactional
	public LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo) {
		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, attributes);

		if (ObjectUtils.isEmpty(oAuth2UserInfo.getEmail())) {
			throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
		}

		SignUpRequest signUpRequest = toUserRegistrationObject(oAuth2UserInfo);
		User user = findByEmail(signUpRequest.getEmail());

		if (user == null) {
			user = registerNewUser(signUpRequest);
		}

		return LocalUser.create(user, attributes, idToken, userInfo);
	}

	@Override
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public User findByEmail(final String email) {
		return userRepository.findByEmail(email);
	}

	private SignUpRequest toUserRegistrationObject(OAuth2UserInfo oAuth2UserInfo) {
		return SignUpRequest.builder()
				.email(oAuth2UserInfo.getEmail())
				.displayName(oAuth2UserInfo.getName())
				.build();
	}
}
