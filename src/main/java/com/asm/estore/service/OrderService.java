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

    @Autowired
    private ModelMapper mapper;

    @Autowired
    public OrderService(
            OrderRepository repository,
            ClientRepository clientRepository,
            OrderProductRepository orderProductRepository
    ) {
        this.orderRepository = repository;
        this.clientRepository = clientRepository;
        this.orderProductRepository = orderProductRepository;
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public List<OrderDTO> getAllByClientId(Long clientId) {
        Optional<List<Order>> opt = orderRepository.findAllClientsOrders(clientId);
        if (opt.isEmpty())
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Clients has no Orders");
        return opt.get().stream().map(
                order -> mapper.map(order, OrderDTO.class)
        ).toList();
    }

    public void createNewOrder(Long clientId) {

        if (clientRepository.findById(clientId).isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This Client Id: " + clientId + " doesn't exists");

        Optional<List<Order>> opt =  orderRepository.findAllClientsOrders(clientId);
        opt.ifPresent(orders -> orders.forEach(
                order -> {
                    if (!order.getFinalized())
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "Client has an Order not finalized if Id: " + order.getId());
                }
        ));
        Order order = new Order(clientId, 0.00F);
        try {
            orderRepository.save(order);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.toString());
        }
    }

    @Transactional
    public void finalizeOrder(Long orderId) {
        orderRepository.findById(orderId).ifPresentOrElse(
            order -> {
                if (order.getFinalized())
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Order already finalized");
                
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

            }, () -> {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
            }
        );
    }
}
