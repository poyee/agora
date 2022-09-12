package com.poyee.agora.bean;

import lombok.Data;

import java.util.List;

@Data
public class CommentDto {
    private Long id;
    private String body;
    private Long pollId;

    private String createdBy;
    private String createdTime;
    private List<Integer> votes;
}
