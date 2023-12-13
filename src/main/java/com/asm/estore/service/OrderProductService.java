package com.asm.estore.service;

import com.asm.estore.dto.order.OrderProductDTO;
import com.asm.estore.entity.Order;
import com.asm.estore.entity.OrderProduct;
import com.asm.estore.entity.Product;
import com.asm.estore.repository.OrderProductRepository;
import com.asm.estore.repository.OrderRepository;
import com.asm.estore.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class OrderProductService {
    private final OrderProductRepository orderProductRepo;
    private final OrderRepository orderRepo;
    private final ProductRepository productRepository;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    public OrderProductService(
            OrderProductRepository orderProductRepo,
            OrderRepository orderRepo,
            ProductRepository productRepository
            ) {
        this.orderProductRepo = orderProductRepo;
        this.orderRepo = orderRepo;
        this.productRepository = productRepository;
    }

    public List<OrderProduct> getAllOrderProducts() {
        return orderProductRepo.findAll();
    }

    public List<OrderProduct> getByOrderId(Long id) {
        Optional<List<OrderProduct>> opt = orderProductRepo.findByOrderId(id);

        if (opt.isEmpty() || opt.get().isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Found no order_product with this Order ID");

        return opt.get();

    }

    public void addProductToOrder(OrderProductDTO dto) {
        if (dto.getFkOrderId() ==  null || dto.getFkProductId() == null || dto.getAmount() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing required fields");
        }

        orderRepo.findById(dto.getFkOrderId()).ifPresentOrElse(
            order -> {
                if (order.getFinalized())
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Order of id: " + dto.getFkOrderId() + " already finalized");

                Optional<OrderProduct> foundOrderProduct =
                        orderProductRepo.findOrderProductByOrderIdProductId(dto.getFkOrderId(), dto.getFkProductId());

                if (foundOrderProduct.isPresent()) {
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, "This Product Id: " + dto.getFkProductId() +
                            " has alredy been added to this Order Id: " + dto.getFkOrderId()
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

                                orderProductRepo.save(orderProduct);
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
