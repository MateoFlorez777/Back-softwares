package com.softwares.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer totalItem;
    private Integer totalPrice;
    private Integer totalDiscountPrice;

    private Integer discount;
    private Integer totalAmount;

    private LocalDateTime orderDate = LocalDateTime.now();

    @Column(name = "payment_status")
    private String paymentStatus = "PENDING"; // 🟢 Estado de pago

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();
}
