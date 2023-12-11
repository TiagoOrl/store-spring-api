package com.asm.estore.controller;

import com.asm.estore.dto.AddCategoryDTO;
import com.asm.estore.entity.ProductCategory;
import com.asm.estore.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/category")
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductCategory> getAllCategories() {
        return service.getAll();
    }

    @PostMapping("add")
    public void addCategory(@RequestBody AddCategoryDTO dto) {
        service.addCategory(dto);
    }
}
