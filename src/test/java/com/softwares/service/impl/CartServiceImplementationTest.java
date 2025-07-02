package com.softwares.service.impl;

import com.softwares.exceptions.ProductException;
import com.softwares.models.*;
import com.softwares.repository.CartItemRepository;
import com.softwares.repository.CartRepository;
import com.softwares.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CartServiceImplementationTest {

    @InjectMocks
    private CartServiceImplementation cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ProductService productService;

    private User user;
    private Product product;
    private Cart cart;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);

        product = new Product();
        product.setSellingPrice(100);
        product.setMrpPrice(150);

        cart = new Cart();
        cart.setId(1L);
        cart.setUser(user);
        cart.setCartItems(new HashSet<>());
    }

    @Test
    void testFindUserCart_returnsCorrectCart() {
        CartItem item = new CartItem();
        item.setMrpPrice(200);
        item.setSellingPrice(150);
        item.setQuantity(2);

        cart.setCartItems(new HashSet<>());
        cart.getCartItems().add(item);

        when(cartRepository.findByUserId(user.getId())).thenReturn(cart);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart result = cartService.findUserCart(user);

        assertNotNull(result);
        assertEquals(200, result.getTotalMrpPrice());
        assertEquals(150, result.getTotalSellingPrice());
        assertEquals(2, result.getTotalItem()); // ← cantidad, no número de ítems
    }




    @Test
    void testAddCartItem_whenItemExists_returnsExistingItem() throws ProductException {
        CartItem existingItem = new CartItem();
        existingItem.setId(20L);

        cart.setCartItems(new HashSet<>());
        when(cartRepository.findByUserId(user.getId())).thenReturn(cart);
        when(cartItemRepository.findByCartAndProductAndSize(any(), any(), any())).thenReturn(existingItem);

        CartItem result = cartService.addCartItem(user, product, "M", 2);

        assertNotNull(result);
        assertEquals(20L, result.getId());
        verify(cartItemRepository, never()).save(any(CartItem.class));
    }

    @Test
    void testClearCart_clearsCartSuccessfully() {
        CartItem item1 = new CartItem();
        CartItem item2 = new CartItem();
        HashSet<CartItem> items = new HashSet<>();
        items.add(item1);
        items.add(item2);

        cart.setCartItems(items);
        cart.setTotalItem(2);
        cart.setTotalMrpPrice(300);
        cart.setTotalSellingPrice(200);
        cart.setDiscount(15);
        cart.setCouponPrice(10);

        when(cartRepository.findByUserId(user.getId())).thenReturn(cart);

        cartService.clearCart(user);

        assertEquals(0, cart.getCartItems().size());
        assertEquals(0, cart.getTotalItem());
        assertEquals(0, cart.getTotalMrpPrice());
        assertEquals(0, cart.getTotalSellingPrice());
        assertEquals(0, cart.getDiscount());
        assertEquals(0, cart.getCouponPrice());
    }
}
