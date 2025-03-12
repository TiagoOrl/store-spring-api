package com.asm.estore.utils;

import lombok.Getter;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public class PaginationUtil {
    public Integer page = 0;
    public Integer size = 30;
    @Getter
    private final PageRequest pageRequest;
    public PaginationUtil(
            Optional<Integer> pageOpt,
            Optional<Integer> sizeOpt
    ) {
        if (pageOpt.isPresent() && sizeOpt.isPresent()) {
            page = pageOpt.get();
            size = sizeOpt.get();
            if (size > 30 || size < 1)
                size = 30;

            if (page < 0)
                page = 0;

        }

        pageRequest = PageRequest.of(page, size);
    }

}
