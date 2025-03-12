package com.asm.estore.service;

import com.asm.estore.dto.product.AddProductDTO;
import com.asm.estore.dto.ListContainerDTO;
import com.asm.estore.dto.product.ProductDTO;
import com.asm.estore.dto.product.UpdateProductDTO;
import com.asm.estore.entity.Product;
import com.asm.estore.repository.ProductCategoryRepository;
import com.asm.estore.repository.ProductRepository;
import com.asm.estore.utils.PaginationUtil;
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


    @Autowired // constructor annotation for dependency injection
    public ProductService(
            ProductRepository repository,
            ProductCategoryRepository categoryRepository,
            ModelMapper mapper
    ) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    public ListContainerDTO<ProductDTO> getAll(
            Optional<Integer> page,
            Optional<Integer> size
    ) {
        var pagUtils = new PaginationUtil(page, size);

        var products = repository.findAll(pagUtils.getPageRequest()).stream().map(
                p -> mapper.map(p, ProductDTO.class)
        ).toList();

        var listCount = repository.count();
        return new ListContainerDTO<ProductDTO>(pagUtils.page, pagUtils.size, (int) (listCount / pagUtils.size), (int) listCount, products);
    }

    public ListContainerDTO<ProductDTO> getByCatId(
            Long catId,
            Optional<Integer> page,
            Optional<Integer> size
    ) {
        var pagUtils = new PaginationUtil(page, size);

        var opt = repository.findByCatId(catId, pagUtils.getPageRequest());

        if (opt.isEmpty() || opt.get().isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        var products =  opt.get().stream().map(
                p -> mapper.map(p, ProductDTO.class)
        ).toList();

        var listCount = opt.get().size();
        return new ListContainerDTO<ProductDTO>(pagUtils.page, pagUtils.size, (listCount / pagUtils.size), listCount, products);
    }

    public ListContainerDTO<ProductDTO> getByName(
            String name,
            Optional<Integer> page,
            Optional<Integer> size
    ) {
        var pagUtils = new PaginationUtil(page, size);

        Optional<List<Product>> opt =  repository.findAllByName(name.toUpperCase().strip(), pagUtils.getPageRequest());
        if (opt.isEmpty() || opt.get().isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        var products =  opt.get().stream().map(i -> mapper.map(i, ProductDTO.class)).toList();

        var listCount = opt.get().size();
        return new ListContainerDTO<ProductDTO>(pagUtils.page, pagUtils.size, (listCount / pagUtils.size), listCount, products);
    }

    public ProductDTO getById(Long id) {
        var optProduct = repository.findProductById(id);

        if (optProduct.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id " + id + " not found");

        return mapper.map(optProduct.get(), ProductDTO.class);
    }

    public AddProductDTO addProduct(AddProductDTO dto) {
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
        product.setCategory(optCategory.get());
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
                                i -> product.setCategory(i),
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
}
