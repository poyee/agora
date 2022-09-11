package com.poyee.agora.bean;

import lombok.Data;

import java.util.List;

@Data
public class PollDto {
    private Long id;
    private String title;
    private String description;
    private List<OptionDto> options;
    private boolean multiVote;
    private boolean allowNewOption;

    //TODO: we have multivotes in the future
    private List<Integer> selectedOptions;

    private String createdBy;
    private String createdTime;
}
