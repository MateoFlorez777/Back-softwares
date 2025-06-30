package com.softwares.service;

import com.softwares.models.Order;
import com.softwares.models.User;

import java.util.List;

public interface OrderService {
	Order createOrder(User user);
	List<Order> getOrdersByUser(User user);
}


