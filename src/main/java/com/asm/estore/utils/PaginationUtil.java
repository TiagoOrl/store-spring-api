package com.asm.estore.utils;

import lombok.Getter;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public class PaginationUtil {
    private Integer page = 0;
    private Integer size = 30;
    @Getter
    private final PageRequest pageRequest;
    public PaginationUtil(
            Optional<Integer> pageOpt,
            Optional<Integer> sizeOpt
    ) {
        if (pageOpt.isPresent() && sizeOpt.isPresent()) {
            page = pageOpt.get();
            size = sizeOpt.get();
            if (size > 30)
                size = 30;
        }

        pageRequest = PageRequest.of(page, size);
    }

}
