package com.softwares.service;

import com.softwares.models.OrderItem;
import java.util.List;

public interface OrderItemService {

	OrderItem getOrderItemById(Long id) throws Exception;

	List<OrderItem> getSoldItemsBySellerId(Long sellerId); // ✅ asegúrate que el tipo sea List<OrderItem>
}
