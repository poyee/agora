package com.poyee.agora.poll.bean;

import lombok.Data;

import java.util.List;

@Data
public class PollRequest {
    private String title;
    private String description;
    private List<String> options;
    private Boolean multiVote;
    private Boolean allowNewOption;
}
