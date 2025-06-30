package com.softwares.repository;

import com.softwares.dto.SoldItemDto;
import com.softwares.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("""
        SELECT new com.softwares.dto.SoldItemDto(
            oi.productName,
            oi.productImage,
            oi.price,
            oi.quantity,
            oi.size,
            o.user.fullName,
            o.orderDate
        )
        FROM OrderItem oi
        JOIN oi.order o
        JOIN Product p ON oi.productId = p.id
        WHERE p.seller.id = :sellerId
    """)
    List<SoldItemDto> getSoldItemsBySellerId(@Param("sellerId") Long sellerId);
}