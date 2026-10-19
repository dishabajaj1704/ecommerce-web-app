package com.ecommerce.user.controllers;

import com.ecommerce.user.dtos.UserRequest;
import com.ecommerce.user.dtos.UserResponse;
import com.ecommerce.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@RequiredArgsConstructor  //generates a constructor with required arguments (final fields)
@RestController
@RequestMapping("/api/users")  //Define base URL for all endpoints in this controller
public class UserController {

    private final UserService userService;
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String id) {

        return  userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {

        UserResponse createdUser = userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody UserRequest userRequest) {

       boolean updated= userService.updateUser(id,userRequest);
       if(updated){
              return ResponseEntity.ok("User updated successfully");
       }
        return ResponseEntity.notFound().build();

    }
}
