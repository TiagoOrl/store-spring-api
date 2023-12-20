package com.asm.estore.service;

import com.asm.estore.dto.product.AddProductDTO;
import com.asm.estore.dto.product.ProductDTO;
import com.asm.estore.dto.product.UpdateProductDTO;
import com.asm.estore.entity.Product;
import com.asm.estore.repository.ProductCategoryRepository;
import com.asm.estore.repository.ProductRepository;
import com.asm.estore.validation.MainValidator;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component // annotation for DI
public class ProductService {
    private final ProductRepository repository;
    private final ProductCategoryRepository categoryRepository;
    private final ModelMapper mapper;
    private final MainValidator mainValidator;


    @Autowired // constructor annotation for dependency injection
    public ProductService(
            ProductRepository repository,
            ProductCategoryRepository categoryRepository,
            ModelMapper mapper,
            MainValidator mainValidator
    ) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
        this.mainValidator = mainValidator;
    }

    public List<ProductDTO> getAll() {
        return repository.findAll().stream().map(
                p -> mapper.map(p, ProductDTO.class)
        ).toList();
    }

    public AddProductDTO addProduct(AddProductDTO dto) {
        mainValidator.validateObject(dto);

        repository.findProductByName(dto.getName()).ifPresent(
                i -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "This Product name already exists");
                }
        );

        var optCategory = categoryRepository.findById(dto.getCategoryId());
        if (optCategory.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Category: " + dto.getCategoryId() + " not found");

        var product = mapper.map(dto, Product.class);
        repository.save(product);

        dto.setId(product.getId());
        return dto;
    }

    public void deleteById(Long id) {
        if (!repository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id: " + id + " not found" );

        repository.deleteById(id);
    }

    @Transactional // specify this to roll back all completed transactions in case of errors in between
    public UpdateProductDTO updateProductById(
            Long productId,
            UpdateProductDTO dto
    ) {
        mainValidator.validateObject(dto);

        repository.findById(productId).ifPresentOrElse(
                product -> {
                    if (dto.getName() != null) {
                        repository.findProductByName(dto.getName()).ifPresent(
                                i -> {
                                    throw new ResponseStatusException(HttpStatus.CONFLICT,
                                    "Product with this name already exists");
                                }
                        );
                        product.setName(dto.getName());
                    }

                    if (dto.getDescription() != null)
                        product.setDescription(dto.getDescription());
                    if (dto.getUnitPrice() != null)
                        product.setUnitPrice(dto.getUnitPrice());
                    if (dto.getImageUrl() != null)
                        product.setImageUrl(dto.getImageUrl());
                    if (dto.getUnitsInStock() != null)
                        product.setUnitsInStock(dto.getUnitsInStock());
                    if (dto.getActive() != null)
                        product.setActive(dto.getActive());
                    if (dto.getCategoryId() != null) {
                        categoryRepository.findById(dto.getCategoryId()).ifPresentOrElse(
                                i -> product.setCategoryId(dto.getCategoryId()),
                                () -> {
                                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                                            "Category id: " + dto.getCategoryId() + " not found"
                                            );
                                }
                        );
                    }


                    product.setUpdatedAt(new Date());
                },
                () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "product id: " + productId + " not found" );
                }
        );

        return dto;
    }

    public List<Product> getByName(String name) {
        if (name == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        Optional<List<Product>> opt =  repository.findAllByName(name.toUpperCase().strip());
        if (opt.isEmpty() || opt.get().isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return opt.get();
    }
}
