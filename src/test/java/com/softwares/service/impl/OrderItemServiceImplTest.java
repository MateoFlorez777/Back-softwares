package com.softwares.service.impl;

import com.softwares.models.OrderItem;
import com.softwares.models.Product;
import com.softwares.models.Seller;
import com.softwares.repository.OrderItemRepository;
import com.softwares.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderItemServiceImplTest {

    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetOrderItemById_whenExists_returnsOrderItem() throws Exception {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);

        when(orderItemRepository.findById(1L)).thenReturn(Optional.of(orderItem));

        OrderItem result = orderItemService.getOrderItemById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetOrderItemById_whenNotExists_throwsException() {
        when(orderItemRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            orderItemService.getOrderItemById(1L);
        });

        assertTrue(exception.getMessage().contains("Order item not found with id: 1"));
    }

    @Test
    void testGetSoldItemsBySellerId_returnsCorrectItems() {
        // Arrange
        Seller seller = new Seller();
        seller.setId(100L);

        Product product = new Product();
        product.setId(10L);
        product.setSeller(seller);

        OrderItem item1 = new OrderItem();
        item1.setId(1L);
        item1.setProductId(10L);

        OrderItem item2 = new OrderItem();
        item2.setId(2L);
        item2.setProductId(20L); // este no es del seller

        List<OrderItem> mockOrderItems = Arrays.asList(item1, item2);

        when(orderItemRepository.findAll()).thenReturn(mockOrderItems);
        when(productRepository.findById(10L)).thenReturn(Optional.of(product));
        when(productRepository.findById(20L)).thenReturn(Optional.empty());

        // Act
        List<OrderItem> result = orderItemService.getSoldItemsBySellerId(100L);

        // Assert
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }
}
