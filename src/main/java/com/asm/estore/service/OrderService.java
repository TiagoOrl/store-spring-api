package com.asm.estore.service;

import com.asm.estore.dto.order.OrderDTO;
import com.asm.estore.entity.Order;
import com.asm.estore.entity.OrderProduct;
import com.asm.estore.entity.Product;
import com.asm.estore.repository.ClientRepository;
import com.asm.estore.repository.OrderProductRepository;
import com.asm.estore.repository.OrderRepository;
import com.asm.estore.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Component
public class OrderService {
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final OrderProductRepository orderProductRepository;
    private ModelMapper mapper;

    @Autowired
    public OrderService(
            OrderRepository repository,
            ClientRepository clientRepository,
            OrderProductRepository orderProductRepository,
            ModelMapper mapper
    ) {
        this.orderRepository = repository;
        this.clientRepository = clientRepository;
        this.orderProductRepository = orderProductRepository;
        this.mapper = mapper;
    }

    public List<OrderDTO> getAll(Integer page, Integer size) {
        if (size > 30)
            size = 30;

        var pageable = PageRequest.of(page, size);
        return orderRepository.findAll(pageable).stream().map(
                order -> mapper.map(order, OrderDTO.class)
        ).toList();
    }

    public List<OrderDTO> getAllByClientId(Long clientId) {
        Optional<List<Order>> opt = orderRepository.findAllClientsOrders(clientId);
        if (opt.isEmpty())
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Clients has no Orders");
        return opt.get().stream().map(
                order -> mapper.map(order, OrderDTO.class)
        ).toList();
    }

    public OrderDTO createNewOrder(Long clientId) {

        var clientOpt = clientRepository.findById(clientId);
        if (clientOpt.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This Client Id: " + clientId + " doesn't exists");

        Optional<List<Order>> opt =  orderRepository.findAllClientsOrders(clientId);
        opt.ifPresent(orders -> orders.forEach(
                order -> {
                    if (!order.getFinalized())
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "Client has an Order not finalized of Id: " + order.getId());
                }
        ));

        Order order = new Order(clientOpt.get());

        try {
            orderRepository.save(order);
            return mapper.map(order, OrderDTO.class);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.toString());
        }
    }

    public Order getByOrderId(Long id) {
        Optional<Order> optOrder = orderRepository.findById(id);
        if (optOrder.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No order found with this Id");

        return optOrder.get();
    }

    @Transactional
    public OrderDTO finalizeOrder(Long orderId) {
        Optional<Order> optOrder = orderRepository.findById(orderId);
        if (optOrder.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");

        Order order = optOrder.get();

        if (order.getFinalized())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Order already finalized");

        if (order.getProducts().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This order has no products added.");

        order.getProducts().forEach(product -> {
            orderProductRepository.findOrderProductByOrderIdProductId(orderId, product.getId()).ifPresent(
                    orderProduct -> {
                        product.setUnitsInStock(
                                product.getUnitsInStock() - orderProduct.getAmount()
                        );
                    }
            );
        });

        order.setFinalized(true);
        return mapper.map(order, OrderDTO.class);
    }
}
