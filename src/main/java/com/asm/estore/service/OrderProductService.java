package com.asm.estore.service;

import com.asm.estore.dto.order.OrderProductDTO;
import com.asm.estore.entity.OrderProduct;
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
public class OrderProductService {
    private final OrderProductRepository orderProductRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    public OrderProductService(
            OrderProductRepository orderProductRepository,
            OrderRepository orderRepository,
            ProductRepository productRepository
            ) {
        this.orderProductRepository = orderProductRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public List<OrderProduct> getAllOrderProducts() {
        return orderProductRepository.findAll();
    }

    public List<OrderProduct> getByOrderId(Long id) {
        Optional<List<OrderProduct>> opt = orderProductRepository.findByOrderId(id);

        if (opt.isEmpty() || opt.get().isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Found no order_product with this Order ID");

        return opt.get();

    }

    /**
     * Adds a Product to an existing Order
     * @param dto Contains the OrderId and ProductId
     */
    @Transactional
    public void addOrderProduct(OrderProductDTO dto) {
        if (dto.getFkOrderId() ==  null || dto.getFkProductId() == null || dto.getAmount() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing required fields");
        }

        orderRepository.findById(dto.getFkOrderId()).ifPresentOrElse(
            order -> {
                if (order.getFinalized())
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Order of id: " + dto.getFkOrderId() + " already finalized");

                Optional<OrderProduct> foundOrderProduct =
                        orderProductRepository.findOrderProductByOrderIdProductId(dto.getFkOrderId(), dto.getFkProductId());

                if (foundOrderProduct.isPresent()) {
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, "This Product Id: " + dto.getFkProductId() +
                            " has already been added to this Order Id: " + dto.getFkOrderId()
                        );
                } else {
                    OrderProduct orderProduct = mapper.map(dto, OrderProduct.class);
                    productRepository.findById(dto.getFkProductId()).ifPresentOrElse(
                            (product) -> {
                                order.setTotalSum(
                                        order.getTotalSum() +
                                        product.getUnitPrice() *
                                        dto.getAmount()
                                );

                                orderProductRepository.save(orderProduct);
                            },  () -> {
                                throw new ResponseStatusException(HttpStatus.CONFLICT, "This Product of Id: " + dto.getFkProductId() +  " doesn't exists in DB");
                            }
                    );
                }
            }, () -> {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order of id: "+ dto.getFkOrderId() + " Not found");
            }
        );

    }
}
