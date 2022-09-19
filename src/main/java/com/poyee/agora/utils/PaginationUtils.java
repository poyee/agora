package com.poyee.agora.utils;

import com.poyee.agora.bean.Pagination;
import org.springframework.data.domain.Page;

import java.util.List;

public class PaginationUtils {

    public static <T> Pagination<T> toPagination(Page<?> page, List<T> contents) {
        Pagination<T> newPagination = new Pagination<>();
        newPagination.setPage(page.getNumber());
        newPagination.setPageSize(page.getSize());
        newPagination.setTotalContents(page.getTotalElements());
        newPagination.setTotalPages(page.getTotalPages());
        newPagination.setContent(contents);

        return newPagination;
    }
}
