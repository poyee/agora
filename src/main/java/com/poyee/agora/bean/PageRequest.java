package com.poyee.agora.bean;

import lombok.Data;

@Data
public class PageRequest {
    private Integer page = 0;
    private Integer pageSize = 5;
}
