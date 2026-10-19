package com.example.order.dtos;


import lombok.Data;

import java.math.BigDecimal;


@Data
public class CartItemResponse {

    private Integer quantity;
    private BigDecimal price;
}
