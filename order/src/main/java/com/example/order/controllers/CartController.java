package com.example.order.controllers;





import com.example.order.dtos.CartItemRequest;
import com.example.order.models.CartItem;
import com.example.order.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    //Read the HTTP request header named X-User-Id and assign its value to the variable userId
    //Why do we pass userId in the header? Because in a real application, we would authenticate the user and get their ID from the authentication context.
    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader("X-User-ID") String userId, @RequestBody CartItemRequest cartItemRequest) {
        // Implementation for adding to cart goes here

        if(!cartService.addToCart(userId,cartItemRequest)){
            return ResponseEntity.badRequest().body("Product Out of Stock or User/Product not found");
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();


    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(@RequestHeader("X-User-ID") String userId, @PathVariable String productId) {
        // Implementation for removing from cart goes here
        boolean deleted= cartService.removeFromCart(userId,productId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }


    @GetMapping
    public ResponseEntity<List<CartItem>> getCartItems(@RequestHeader("X-User-ID") String userId) {
            return ResponseEntity.ok(cartService.getCartItems(userId));
    }
}
