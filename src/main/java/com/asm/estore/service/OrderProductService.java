package com.asm.estore.service;

import com.asm.estore.dto.order.DeleteOrderProductDTO;
import com.asm.estore.dto.order.OrderProductDTO;
import com.asm.estore.dto.order.UpdateOrderProductDTO;
import com.asm.estore.entity.Order;
import com.asm.estore.entity.OrderProduct;
import com.asm.estore.entity.Product;
import com.asm.estore.repository.OrderProductRepository;
import com.asm.estore.repository.OrderRepository;
import com.asm.estore.repository.ProductRepository;
import com.asm.estore.utils.PaginationUtil;
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
    private final ModelMapper mapper;

    @Autowired
    public OrderProductService(
            OrderProductRepository orderProductRepository,
            OrderRepository orderRepository,
            ProductRepository productRepository,
            ModelMapper mapper
            ) {
        this.orderProductRepository = orderProductRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    public List<OrderProduct> getAllOrderProducts(
            Optional<Integer> page,
            Optional<Integer> size
    ) {
        var pagUtils = new PaginationUtil(page, size);
        return orderProductRepository.findAll(pagUtils.getPageRequest()).stream().toList();
    }

    public List<OrderProductDTO> getAllByOrderId(Long orderId) {
        Optional<List<OrderProduct>> optOrderProducts = orderProductRepository.findAllByOrderId(orderId);

        if (optOrderProducts.isEmpty() || optOrderProducts.get().isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No OrderProduct found with this OrderId");

        return optOrderProducts.get().stream().map(
                orderProduct -> mapper.map(orderProduct, OrderProductDTO.class)
        ).toList();

    }

    /**
     * Adds a Product to an existing Order
     * @param dto Contains the OrderId and ProductId
     */
    @Transactional
    public List<OrderProductDTO> addOrderProduct(List<OrderProductDTO> dtos) {

        dtos.forEach(dto -> {
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
                            var newOrderProduct = mapper.map(dto, OrderProduct.class);
                            productRepository.findById(dto.getFkProductId()).ifPresentOrElse(
                                    (product) -> {
                                        if (dto.getAmount() > product.getUnitsInStock())
                                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                                    "This product order: " + product.getName() +
                                                            " amount requested: " + dto.getAmount() +
                                                            ", exceeds the stock count: " + product.getUnitsInStock()
                                            );

                                        order.setTotalSum(
                                                order.getTotalSum() +
                                                        product.getUnitPrice() *
                                                                dto.getAmount()
                                        );

                                        newOrderProduct.setProductName(product.getName());
                                        newOrderProduct.setUnitPrice(product.getUnitPrice());
                                        orderProductRepository.save(newOrderProduct);
                                    },  () -> {
                                        throw new ResponseStatusException(HttpStatus.CONFLICT, "This Product of Id: " + dto.getFkProductId() +  " doesn't exists in DB");
                                    }
                            );
                        }
                    }, () -> {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order of id: "+ dto.getFkOrderId() + " Not found");
                    }
            );
        });

        return dtos;
    }

    @Transactional
    public UpdateOrderProductDTO changeAmount(UpdateOrderProductDTO dto) {
        Optional<OrderProduct> optOrderProduct =
                orderProductRepository.findOrderProductByOrderIdProductId(dto.getFkOrderId(), dto.getFkProductId());

        Optional<Product> optProduct = productRepository.findById(dto.getFkProductId());
        Optional<Order> optOrder = orderRepository.findById(dto.getFkOrderId());

        if (optOrderProduct.isEmpty() || optOrder.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order of id: "+ dto.getFkOrderId() + " Not found.");
        if (optProduct.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product of id: " + dto.getFkProductId() + " Not found.");

        Product product = optProduct.get();
        Order order = optOrder.get();
        OrderProduct orderProduct = optOrderProduct.get();

        if (order.getFinalized())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Order of id: " + dto.getFkOrderId() + " already finalized");

        if (dto.getAmount() > product.getUnitsInStock())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "This product order: " + product.getName() +
                " amount requested: " + dto.getAmount() +
                "exceeds the stock count: " + product.getUnitsInStock()
            );

        float deductedSum = order.getTotalSum() - orderProduct.getAmount() * orderProduct.getUnitPrice();
        orderProduct.setAmount(dto.getAmount());
        order.setTotalSum(deductedSum + dto.getAmount() * orderProduct.getUnitPrice());

        return mapper.map(orderProduct, UpdateOrderProductDTO.class);
    }

    @Transactional
    public DeleteOrderProductDTO deleteByOrderIdProductId(DeleteOrderProductDTO dto) {

        if (dto.getFkOrderId() == null || dto.getFkProductId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing order id or product id");

        var optOrderProduct = orderProductRepository.findOrderProductByOrderIdProductId(dto.getFkOrderId(), dto.getFkProductId());
        if (optOrderProduct.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "order product not found");

        OrderProduct orderProduct = optOrderProduct.get();

        orderRepository.findById(dto.getFkOrderId()).ifPresentOrElse(
                order -> {
                    if (order.getFinalized())
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "Order already finalized");

                    Float newTotalSum = order.getTotalSum() - orderProduct.getUnitPrice() * orderProduct.getAmount();
                    order.setTotalSum(newTotalSum);
                },
                () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "order not found");
                }
        );

        orderProductRepository.delete(orderProduct);
        return mapper.map(orderProduct, DeleteOrderProductDTO.class);
    }
}
