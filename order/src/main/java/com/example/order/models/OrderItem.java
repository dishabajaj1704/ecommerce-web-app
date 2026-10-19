package com.example.order.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

   private String productId;
    private BigDecimal price;

    @ManyToOne   //Many order items can belong to one order. For example, a phone order can have multiple items like phone case, charger, etc.
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;


}
