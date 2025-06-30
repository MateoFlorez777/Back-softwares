package com.softwares.service;


import com.softwares.exceptions.CartItemException;
import com.softwares.exceptions.UserException;
import com.softwares.models.CartItem;

public interface CartItemService {
	
	public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException;
	
	public void removeCartItem(Long userId,Long cartItemId) throws CartItemException, UserException;
	
	public CartItem findCartItemById(Long cartItemId) throws CartItemException;
	
}
