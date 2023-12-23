package com.asm.estore.service;


import com.asm.estore.dto.category.AddCategoryDTO;
import com.asm.estore.dto.category.CategoryDTO;
import com.asm.estore.entity.ProductCategory;
import com.asm.estore.repository.ProductCategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class CategoryService {
    private final ProductCategoryRepository repository;
    private final ModelMapper mapper;

    @Autowired
    public CategoryService(ProductCategoryRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<CategoryDTO> getAll() {
        return repository.findAll().stream().map(
                category -> mapper.map(category, CategoryDTO.class)
        ).toList();
    }

    public void addCategory(AddCategoryDTO dto) {
        if (dto.getName() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name field is null");

        if (repository.findByName(dto.getName()).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This name category already exists");

        ProductCategory category = new ProductCategory(dto.getName());
        repository.save(category);
    }
}
