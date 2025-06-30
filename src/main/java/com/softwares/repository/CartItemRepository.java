package com.softwares.repository;


import com.softwares.models.Cart;
import com.softwares.models.CartItem;
import com.softwares.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartItemRepository extends JpaRepository<CartItem, Long> {


    CartItem findByCartAndProductAndSize(Cart cart, Product product, String size);


}
