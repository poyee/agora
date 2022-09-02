package com.poyee.agora.security.oauth2.user;

import com.poyee.agora.bean.SocialProvider;
import com.poyee.agora.exception.auth.OAuth2AuthenticationProcessingException;

import java.util.Map;

public class OAuth2UserInfoFactory {
	public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
		if (registrationId.equalsIgnoreCase(SocialProvider.GOOGLE.getProviderType())) {
			return new GoogleOAuth2UserInfo(attributes);
		} else {
			throw new OAuth2AuthenticationProcessingException("login with " + registrationId + " is not supported yet.");
		}
	}
}
