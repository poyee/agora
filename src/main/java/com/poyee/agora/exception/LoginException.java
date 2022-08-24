package com.poyee.agora.exception;

public class LoginException extends AgoraRuntimeException {
    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, String errorMsg) {
        super(message, errorMsg);
    }
}
