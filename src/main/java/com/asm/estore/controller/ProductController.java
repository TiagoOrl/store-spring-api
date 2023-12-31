package com.asm.estore.controller;

import com.asm.estore.dto.product.AddProductDTO;
import com.asm.estore.dto.product.ProductDTO;
import com.asm.estore.dto.product.SearchProductDTO;
import com.asm.estore.dto.product.UpdateProductDTO;
import com.asm.estore.entity.Product;
import com.asm.estore.service.ProductService;
import jakarta.validation.Valid;
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
    public List<ProductDTO> getAllProducts(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size
            ) {
        return productService.getAll(page, size);
    }

    @GetMapping("get-by-name")
    List<Product> getProductsByName(@RequestBody SearchProductDTO dto) {
        return productService.getByName(dto.getName());
    }

    @PostMapping("add")
    public AddProductDTO addProduct(
            @Valid @RequestBody AddProductDTO product
    ) {
        return productService.addProduct(product);
    }

    @DeleteMapping(path = "{productId}")
    public void deleteProductById(@PathVariable("productId") Long id) {
        productService.deleteById(id);
    }

    @PutMapping(path = "{productId}")
    public UpdateProductDTO updateProductById(
            @PathVariable("productId") Long productId,
            @Valid @RequestBody UpdateProductDTO dto
    ) {
        return productService.updateProductById(productId, dto);
    }
}