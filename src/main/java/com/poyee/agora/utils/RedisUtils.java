package com.poyee.agora.utils;

public class RedisUtils {
    public static String getVoteCountKey(long pollId, long optionNumber) {
        return String.format("poll_%s_option_%s_vote_count", pollId, optionNumber);
    }

    public static String getPollTotalVoteCountKey(Long pollId) {
        return String.format("poll_%s_total_vote_count", pollId);
    }

    public static String getPollCommentCountKey(Long pollId) {
        return String.format("poll_%s_comment_count", pollId);
    }

    public static String getReactCountKey(long questionId, String react) {
        return String.format("poll_%s_react_%s_count", questionId, react);
    }
}
