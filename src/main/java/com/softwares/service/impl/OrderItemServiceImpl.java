package com.softwares.service.impl;

import com.softwares.models.OrderItem;
import com.softwares.repository.OrderItemRepository;
import com.softwares.repository.ProductRepository;
import com.softwares.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderItem getOrderItemById(Long id) throws Exception {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new Exception("Order item not found with id: " + id));
    }

    // ✅ Método para obtener los productos vendidos por un vendedor
    public List<OrderItem> getSoldItemsBySellerId(Long sellerId) {
        return orderItemRepository.findAll().stream()
                .filter(orderItem -> {
                    // Busca el producto relacionado
                    return productRepository.findById(orderItem.getProductId())
                            .filter(product -> product.getSeller() != null && product.getSeller().getId().equals(sellerId))
                            .isPresent();
                })
                .collect(Collectors.toList());
    }
}
