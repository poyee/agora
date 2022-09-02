package com.poyee.agora.exception.auth;

import org.springframework.security.core.AuthenticationException;

/**
 * 
 * @author Chinna
 *
 */
public class UserAlreadyExistAuthenticationException extends AuthenticationException {
	public UserAlreadyExistAuthenticationException(final String msg) {
        super(msg);
    }

}
