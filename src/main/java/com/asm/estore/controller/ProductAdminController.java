package com.asm.estore.controller;


import com.asm.estore.dto.product.AddProductDTO;
import com.asm.estore.dto.product.UpdateProductDTO;
import com.asm.estore.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/product")
public class ProductAdminController {
    private final ProductService productService;

    @Autowired // constructor annotation for dependency injection
    public ProductAdminController(ProductService productService) {
        this.productService = productService;
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
