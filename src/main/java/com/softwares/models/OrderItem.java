package com.softwares.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long productId;
	private String productName;
	private String productImage;
	private String size;
	private Integer price;
	private Integer quantity;



	@ManyToOne
	@JsonIgnore // 🔥 Evita la recursión infinita al serializar
	private Order order;
}

