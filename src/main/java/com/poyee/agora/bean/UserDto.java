package com.poyee.agora.bean;

import lombok.Value;

import java.util.List;

@Value
public class UserDto {
    private long id;
    private String displayName;
    private String email;
    private List<String> roles;
}
