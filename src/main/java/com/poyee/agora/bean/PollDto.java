package com.poyee.agora.bean;

import lombok.Data;

import java.util.List;

@Data
public class PollDto {
    private String title;
    private String description;
    private List<OptionDto> options;
    private boolean multiVote;
    private boolean allowNewOption;
}
