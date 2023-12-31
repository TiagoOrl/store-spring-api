package com.asm.estore.controller;

import com.asm.estore.dto.category.AddCategoryDTO;
import com.asm.estore.dto.category.CategoryDTO;
import com.asm.estore.entity.ProductCategory;
import com.asm.estore.service.CategoryService;
import jakarta.validation.Valid;
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
    public List<CategoryDTO> getAllCategories() {
        return service.getAll();
    }

    @PostMapping("add")
    public void addCategory(@Valid @RequestBody AddCategoryDTO dto) {
        service.addCategory(dto);
    }
}
