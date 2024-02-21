package com.asm.estore.controller;

import com.asm.estore.dto.category.AddCategoryDTO;
import com.asm.estore.dto.category.CategoryDTO;
import com.asm.estore.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/admin/category")
public class CategoryAdminController {
    private final CategoryService service;

    public CategoryAdminController(CategoryService service) {
        this.service = service;
    }


    @PostMapping("add")
    public void addCategory(@Valid @RequestBody AddCategoryDTO dto) {
        service.addCategory(dto);
    }
}
