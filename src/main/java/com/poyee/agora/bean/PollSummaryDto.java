package com.poyee.agora.bean;

import lombok.Data;

import java.util.List;

@Data
public class PollSummaryDto {
    private Long id;
    private String title;
    private List<OptionDto> options;

    private int voteCount;
    private int commentCount;

    private String createdBy;
    private String createdTime;
}
