package com.softwares.service.impl;

import com.softwares.models.*;
import com.softwares.repository.CartItemRepository;
import com.softwares.repository.OrderRepository;
import com.softwares.service.CartService;
import com.softwares.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final CartItemRepository cartItemRepository;

    @Override
    public Order createOrder(User user) {
        Cart cart = cartService.findUserCart(user);

        Order order = new Order();
        order.setUser(user);
        order.setTotalItem(cart.getTotalItem());
        order.setTotalPrice(cart.getTotalMrpPrice());
        order.setTotalDiscountPrice((int) cart.getTotalSellingPrice());
        order.setDiscount(cart.getDiscount());
        order.setTotalAmount((int) cart.getTotalSellingPrice());
        order.setPaymentStatus("PAID"); // 🟢 Establecer como pagado

        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProductId(cartItem.getProduct().getId());
            orderItem.setProductName(cartItem.getProduct().getTitle());
            orderItem.setProductImage(cartItem.getProduct().getImages().isEmpty() ? null : cartItem.getProduct().getImages().get(0)); // ✅
            orderItem.setSize(cartItem.getSize());
            orderItem.setPrice(cartItem.getSellingPrice() / cartItem.getQuantity()); // ✅ PRECIO UNITARIO
            orderItem.setQuantity(cartItem.getQuantity());

            order.getOrderItems().add(orderItem);
        }


        cartService.clearCart(user);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }
}

