package com.asm.estore.controller;

import com.asm.estore.dto.product.AddProductDTO;
import com.asm.estore.dto.product.ProductDTO;
import com.asm.estore.dto.product.SearchProductDTO;
import com.asm.estore.dto.product.UpdateProductDTO;
import com.asm.estore.entity.Product;
import com.asm.estore.service.ProductService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
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
    public ResponseEntity<List<ProductDTO>> getAllProducts(
            @RequestParam(value = "page") Optional<Integer> page,
            @RequestParam(value = "size") Optional<Integer> size
            ) {
        return ResponseEntity.ok(productService.getAll(page, size));
    }

    @PutMapping("get-by-name")
    List<ProductDTO> getProductsByName(
            @Valid @RequestBody SearchProductDTO dto,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size
    ) {
        return productService.getByName(dto.getName(), page, size);
    }

    @GetMapping("get-by-category")
    List<ProductDTO> getByCategory(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size,
            @RequestParam Long catId
    ) {
        return productService.getByCatId(catId, page, size);
    }
}