package com.asm.estore.controller;


import com.asm.estore.entity.OrderProduct;
import com.asm.estore.service.OrderProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/order_product")
public class OrderProductController {

    private final OrderProductService service;

    @Autowired
    public OrderProductController(OrderProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<OrderProduct> getAllOrderProducts() {
        return service.getAll();
    }

    @GetMapping(path = "{id}")
    public List<OrderProduct> getAllByOrderId(
            @PathVariable("id") Long id
    ) {
        return service.getByOrderId(id);
    }
}
