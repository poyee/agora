package com.poyee.agora.bean;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class PollRequest {
    @NotEmpty
    private String title;
    private String description;
    @Size(min=2)
    @Valid
    private List<Option> options;
    private Boolean multiVote;
    private Boolean allowNewOption;

    @Data
    public static class Option {
        @NotEmpty
        private String option;
    }
}
