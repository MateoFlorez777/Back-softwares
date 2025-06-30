package com.softwares.service;




import com.softwares.exceptions.WishlistNotFoundException;
import com.softwares.models.Product;
import com.softwares.models.User;
import com.softwares.models.Wishlist;

import java.util.Optional;

public interface WishlistService {

    Wishlist createWishlist(User user);

    Wishlist getWishlistByUserId(User user);

    Wishlist addProductToWishlist(User user, Product product) throws WishlistNotFoundException;

}

