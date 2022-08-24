package com.poyee.agora.utils;

public class RedisUtils {
    public static String getVoteKey(long questionId, long optionNumber) {
        return String.format("poll_%s_option_%s", questionId, optionNumber);
    }
}
