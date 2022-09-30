package com.poyee.agora.comment.bean;

import lombok.Data;

import java.util.List;

@Data
public class CommentDto {
    private Long id;
    private String body;
    private Long pollId;
    private boolean anonymous;
    private String createdBy;
    private String createdTime;
    private List<Integer> votes;
    private boolean editable;
}
