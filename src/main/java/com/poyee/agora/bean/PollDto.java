package com.poyee.agora.bean;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class PollDto {
    private Long id;
    private String title;
    private String description;
    private List<OptionDto> options;
    private boolean multiVote;
    private boolean allowNewOption;

    //TODO: we have multi votes in the future
    private List<Integer> selectedOptions;

    private String createdBy;
    private String createdTime;

    private Map<String, Integer> reactCount = new HashMap<>();
    private ReactType userReact;
    private boolean editable;
}
