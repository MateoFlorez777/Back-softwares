package com.softwares.service.impl;

import com.softwares.exceptions.WishlistNotFoundException;
import com.softwares.models.Product;
import com.softwares.models.User;
import com.softwares.models.Wishlist;
import com.softwares.repository.WishlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WishlistServiceImplTest {

    @InjectMocks
    private WishlistServiceImpl wishlistService;

    @Mock
    private WishlistRepository wishlistRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateWishlist() {
        User user = new User();
        user.setId(1L);

        Wishlist savedWishlist = new Wishlist();
        savedWishlist.setUser(user);

        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(savedWishlist);

        Wishlist result = wishlistService.createWishlist(user);

        assertNotNull(result);
        assertEquals(user, result.getUser());
        verify(wishlistRepository).save(any(Wishlist.class));
    }

    @Test
    void testGetWishlistByUserId_Exists() {
        User user = new User();
        user.setId(2L);

        Wishlist existingWishlist = new Wishlist();
        existingWishlist.setUser(user);

        when(wishlistRepository.findByUserId(user.getId())).thenReturn(existingWishlist);

        Wishlist result = wishlistService.getWishlistByUserId(user);

        assertNotNull(result);
        assertEquals(existingWishlist, result);
        verify(wishlistRepository).findByUserId(user.getId());
        verify(wishlistRepository, never()).save(any());
    }

    @Test
    void testGetWishlistByUserId_NotExists_CreatesNew() {
        User user = new User();
        user.setId(3L);

        when(wishlistRepository.findByUserId(user.getId())).thenReturn(null);

        Wishlist newWishlist = new Wishlist();
        newWishlist.setUser(user);

        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(newWishlist);

        Wishlist result = wishlistService.getWishlistByUserId(user);

        assertNotNull(result);
        assertEquals(user, result.getUser());

        verify(wishlistRepository).findByUserId(user.getId());
        verify(wishlistRepository).save(any(Wishlist.class));
    }

    @Test
    void testAddProductToWishlist_AddAndRemove() throws WishlistNotFoundException {
        User user = new User();
        user.setId(4L);

        Product product = new Product();
        product.setId(10L);

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setProducts(new HashSet<>());

        when(wishlistRepository.findByUserId(user.getId())).thenReturn(wishlist);

        // Add product (not present)
        Wishlist savedWishlistAdd = new Wishlist();
        savedWishlistAdd.setUser(user);
        savedWishlistAdd.setProducts(new HashSet<>());
        savedWishlistAdd.getProducts().add(product);

        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(savedWishlistAdd);

        Wishlist resultAdd = wishlistService.addProductToWishlist(user, product);

        assertTrue(resultAdd.getProducts().contains(product));

        // Now simulate that product is present, so it should be removed
        wishlist.getProducts().add(product);
        Wishlist savedWishlistRemove = new Wishlist();
        savedWishlistRemove.setUser(user);
        savedWishlistRemove.setProducts(new HashSet<>());

        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(savedWishlistRemove);

        Wishlist resultRemove = wishlistService.addProductToWishlist(user, product);

        assertFalse(resultRemove.getProducts().contains(product));
    }
}
