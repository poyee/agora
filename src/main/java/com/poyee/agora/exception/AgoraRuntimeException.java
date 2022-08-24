package com.poyee.agora.exception;

public abstract class AgoraRuntimeException extends RuntimeException {
    protected String errorMsg;

    public AgoraRuntimeException(String message) {
        this(message, message);
    }

    public AgoraRuntimeException(String message, String errorMsg) {
        super(message);
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
