package com.asm.estore.controller;


import com.asm.estore.dto.category.CategoryDTO;
import com.asm.estore.service.CategoryService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/public/category")
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }


    @GetMapping
    public List<CategoryDTO> getAllCategories(
            Optional<Integer> page,
            Optional<Integer> size
    ) {
        return service.getAll(page, size);
    }
}
