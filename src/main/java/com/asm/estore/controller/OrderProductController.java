package com.asm.estore.controller;


import com.asm.estore.dto.order.OrderProductDTO;
import com.asm.estore.entity.OrderProduct;
import com.asm.estore.service.OrderProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/order_product")
public class OrderProductController {
    private final OrderProductService orderProductService;

    @Autowired
    public OrderProductController(OrderProductService orderProductService) {
        this.orderProductService = orderProductService;
    }

    @GetMapping
    public List<OrderProduct> getAllOrderProducts() {
        return orderProductService.getAllOrderProducts();
    }

    @GetMapping(path = "{orderId}")
    public List<OrderProductDTO> getAllByOrderId(@PathVariable("orderId") Long id) {
        return orderProductService.getAllByOrderId(id);
    }

    @PostMapping(path = "add")
    public OrderProductDTO addProductToOrder(@RequestBody OrderProductDTO dto) {
        return orderProductService.addOrderProduct(dto);
    }

    @PutMapping(path = "update_amount")
    public OrderProductDTO changeProductAmount(@RequestBody OrderProductDTO dto) {
        return orderProductService.changeAmount(dto);
    }

    @DeleteMapping(path = "delete")
    public OrderProductDTO deleteOrderProduct(@RequestBody OrderProductDTO dto) {
        return orderProductService.deleteByOrderIdProductId(dto);
    }
}
