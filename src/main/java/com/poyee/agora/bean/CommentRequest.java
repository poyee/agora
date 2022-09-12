package com.poyee.agora.bean;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CommentRequest {
    @NotNull
    private Long pollId;
    @NotEmpty
    private String body;
}
