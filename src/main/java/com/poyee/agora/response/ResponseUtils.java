package com.poyee.agora.response;

import com.poyee.agora.exception.AgoraRuntimeException;
import org.springframework.http.HttpStatus;

public class ResponseUtils {
    public static Acknowledgement ok(Object rtnObj) {
        return Acknowledgement.builder()
                .ok(true)
                .statusCode(200)
                .rtnObj(rtnObj)
                .build();
    }

    public static Acknowledgement notFound(AgoraRuntimeException ex) {
        return ack(ex, HttpStatus.NOT_FOUND.value());
    }

    public static Acknowledgement conflict(AgoraRuntimeException ex) {
        return ack(ex, HttpStatus.CONFLICT.value());
    }

    public static Acknowledgement forbidden(AgoraRuntimeException ex) {
        return ack(ex, HttpStatus.FORBIDDEN.value());
    }

    private static Acknowledgement ack(AgoraRuntimeException ex, int statusCode) {
        return ack(ex.getErrorMsg(), statusCode);
    }

    public static Acknowledgement ack(String errorMsg, int statusCode) {
        return Acknowledgement.builder()
                .ok(false)
                .statusCode(statusCode)
                .errorMsg(errorMsg)
                .build();
    }
}
