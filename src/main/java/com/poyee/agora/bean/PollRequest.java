package com.poyee.agora.bean;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class PollRequest {
    @NotEmpty
    private String title;
    private String description;
    @Min(2)
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
