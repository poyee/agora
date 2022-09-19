package com.poyee.agora.bean;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdateDisplayNameRequest {
    @NotEmpty
    private String displayName;
}
