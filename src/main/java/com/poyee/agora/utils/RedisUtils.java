package com.poyee.agora.utils;

public class RedisUtils {
    public static String getVoteKey(long questionId, long optionNumber) {
        return String.format("poll_%s_option_%s_vote_count", questionId, optionNumber);
    }

    public static String getPollTotalVoteKey(Long pollId) {
        return String.format("poll_%s_total_vote_count", pollId);
    }

    public static String getPollCommentKey(Long pollId) {
        return String.format("poll_%s_comment_count", pollId);
    }

    public static String getReactKey(long questionId, String react) {
        return String.format("poll_%s_react_%s_count", questionId, react);
    }
}
