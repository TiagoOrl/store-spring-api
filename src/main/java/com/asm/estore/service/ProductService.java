package com.asm.estore.service;

import com.asm.estore.dto.UpdateProductDTO;
import com.asm.estore.entity.Product;
import com.asm.estore.repository.ProductRepository;
import jakarta.transaction.Transactional;
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


    @Autowired // constructor annotation for dependency injection
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAll() {
        return repository.findAll();
    }

    public void addProduct(Product product) {
        Optional<Product> productFound = repository.findProductByName(product.getName());
        if (productFound.isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This name already exists");

        product.setCreatedAt(new Date());
        repository.save(product);
    }

    public void deleteById(Long id) {
        if (!repository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id: " + id + " not found" );

        repository.deleteById(id);
    }

    @Transactional // specify this to roll back all completed transactions in case of errors in between
    public void updateProductById(
            Long id,
            UpdateProductDTO dto
    ) {
        Product product = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "id: " + id + " not found" )
        );

        if (dto.getName() != null && !dto.getName().isEmpty()) {
            Optional<Product> found = repository.findProductByName(dto.getName());

            if (found.isPresent())
                throw new ResponseStatusException(HttpStatus.CONFLICT, "This name already exists");

            product.setName(dto.getName());
        }

        if (dto.getDescription() != null && !dto.getDescription().isEmpty())
            product.setDescription(dto.getDescription());

        if (dto.getUnitPrice() != null)
            product.setUnitPrice(dto.getUnitPrice());

        if (dto.isActive() != null)
            product.setActive(dto.isActive());

        if (dto.getStockCount() != null)
            product.setUnitsInStock(dto.getStockCount());

        product.setUpdatedAt(new Date());
    }
}
