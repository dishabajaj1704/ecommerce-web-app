package com.example.order.models;



import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.example.order.models.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name="orders")
@Data
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)  //Store the enum as a string in the database
    private com.example.order.models.OrderStatus status= com.example.order.models.OrderStatus.PENDING;


    //mappedBy should be the name of the field in the OrderItem class that owns the relationship
    //orphanRemoval=true means if an OrderItem is removed from the orderItems list, it will be deleted from the database
    //If an order is deleted, all its associated order items will also be deleted
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)   //One order can have multiple order items
    private List<OrderItem> orderItems=new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;





}
