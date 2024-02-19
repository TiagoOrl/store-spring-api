package com.asm.estore.controller;

import com.asm.estore.dto.category.AddCategoryDTO;
import com.asm.estore.dto.category.CategoryDTO;
import com.asm.estore.entity.ProductCategory;
import com.asm.estore.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/category")
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

    @PostMapping("admin/add")
    public void addCategory(@Valid @RequestBody AddCategoryDTO dto) {
        service.addCategory(dto);
    }
}
