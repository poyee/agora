package com.poyee.agora.bean;

import lombok.Data;

import java.util.List;

@Data
public class Pagination<T> {
    private int page;
    private int pageSize;
    private int totalPages;
    private long totalContents;
    private List<T> content;
}
