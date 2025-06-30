package com.softwares.dto;

import lombok.Setter;

import java.time.LocalDateTime;


public class SoldItemDto {

    private String productName;
    private String productImage;
    private Integer price;
    private Integer quantity;
    private String size;
    private String buyerName;
    private LocalDateTime orderDate;

    // ✅ Constructor usado en el SELECT new
    public SoldItemDto(String productName, String productImage, Integer price,
                       Integer quantity, String size, String buyerName, LocalDateTime orderDate) {
        this.productName = productName;
        this.productImage = productImage;
        this.price = price;
        this.quantity = quantity;
        this.size = size;
        this.buyerName = buyerName;
        this.orderDate = orderDate;
    }

    // ✅ Getters necesarios para serialización
    public String getProductName() {
        return productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getSize() {
        return size;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }
}
