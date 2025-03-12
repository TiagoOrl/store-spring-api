package com.asm.estore.dto;

import lombok.Data;

import java.util.List;

@Data
public class ListContainerDTO<F> {
    Integer page;
    Integer size;
    Integer totalPages;
    Integer totalElements;
    List<F> list;

    public ListContainerDTO(Integer page, Integer size, Integer totalPages, Integer totalElements, List<F> list) {
        this.page = page;
        this.size = size;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.list = list;
    }
}
