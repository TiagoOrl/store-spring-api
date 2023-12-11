package com.asm.estore.service;


import com.asm.estore.dto.AddCategoryDTO;
import com.asm.estore.entity.ProductCategory;
import com.asm.estore.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class CategoryService {
    private final ProductCategoryRepository repository;

    @Autowired
    public CategoryService(ProductCategoryRepository repository) {
        this.repository = repository;
    }

    public List<ProductCategory> getAll() {
        return repository.findAll();
    }

    public void addCategory(AddCategoryDTO dto) {
        if (dto.getName() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name field is null");

        ProductCategory category = new ProductCategory(dto.getName());
        repository.save(category);
    }
}
