package com.softwares.response;


import com.softwares.dto.OrderHistory;
import com.softwares.models.Cart;
import com.softwares.models.Product;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FunctionResponse {
    private String functionName;
    private Cart userCart;
    private OrderHistory orderHistory;
    private Product product;
}
