package com.poyee.agora.comment.bean;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CommentRequest {
    @NotNull
    private Long pollId;
    @NotEmpty
    @Length(min = 1, max = 200)
    private String body;
    private boolean anonymous;
}
