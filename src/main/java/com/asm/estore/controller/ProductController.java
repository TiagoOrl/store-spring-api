package com.asm.estore.controller;

import com.asm.estore.dto.ListContainerDTO;
import com.asm.estore.dto.product.*;
import com.asm.estore.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/public/product")
public class ProductController {
    private final ProductService productService;

    @Autowired // constructor annotation for dependency injection
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ListContainerDTO<ProductDTO>> getAllProducts(
            @RequestParam(value = "page") Optional<Integer> page,
            @RequestParam(value = "size") Optional<Integer> size
            ) {
        return ResponseEntity.ok(productService.getAll(page, size));
    }

    @PutMapping("get-by-name")
    ResponseEntity<ListContainerDTO<ProductDTO>> getProductsByName(
            @Valid @RequestBody SearchProductDTO dto,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size
    ) {
        return ResponseEntity.ok(productService.getByName(dto.getName(), page, size));
    }

    @GetMapping("get-by-category")
    ResponseEntity<ListContainerDTO<ProductDTO>> getByCategory(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size,
            @RequestParam Long catId
    ) {
        return ResponseEntity.ok(productService.getByCatId(catId, page, size));
    }

    @GetMapping("{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("productId") Long productId ) {
        return ResponseEntity.ok(productService.getById(productId));
    }
}