package com.asm.estore.controller;

import com.asm.estore.dto.AddProductDTO;
import com.asm.estore.dto.UpdateProductDTO;
import com.asm.estore.entity.Product;
import com.asm.estore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product")
public class ProductController {
    private final ProductService productService;

    @Autowired // constructor annotation for dependency injection
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAll();
    }

    @PostMapping("add")
    public void addProduct(@RequestBody AddProductDTO product) {
        productService.addProduct(product);
    }

    @DeleteMapping(path = "{productId}")
    public void deleteProductById(@PathVariable("productId") Long id) {
        productService.deleteById(id);
    }

    @PutMapping(path = "{productId}")
    public void updateProductById(
            @PathVariable("productId") Long id,
            @RequestBody UpdateProductDTO dto
    ) {
        productService.updateProductById(id, dto);
    }
}