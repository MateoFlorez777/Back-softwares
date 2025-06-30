package com.softwares.service;


import com.softwares.exceptions.ProductException;
import com.softwares.models.Cart;
import com.softwares.models.CartItem;
import com.softwares.models.Product;
import com.softwares.models.User;

public interface CartService {
	
	public CartItem addCartItem(User user,
								Product product,
								String size,
								int quantity) throws ProductException;
	
	public Cart findUserCart(User user);
	void clearCart(User user);

}
