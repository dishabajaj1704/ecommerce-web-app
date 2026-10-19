package com.ecommerce.product.services;


import com.ecommerce.product.dtos.ProductRequest;
import com.ecommerce.product.dtos.ProductResponse;
import com.ecommerce.product.models.Product;
import com.ecommerce.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse addProduct(ProductRequest productRequest) {
        Product product = new Product();
        updateProductFromRequest(product, productRequest);
        Product savedProduct =productRepository.save(product);
        return mapToProductResponse(savedProduct);
    }

    public Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest) {

       return productRepository.findById(id)
               .map(existingProduct -> {
                   updateProductFromRequest(existingProduct, productRequest);
                   Product savedProduct=productRepository.save(existingProduct);
                  return mapToProductResponse(savedProduct);
               });
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findByActiveTrue()
                .stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());

    }

    public Optional<ProductResponse> getProductById(Long id) {
       return productRepository.findByActiveTrue()
               .stream()
               .filter(product -> product.getId().equals(id))
               .findFirst()
               .map(this::mapToProductResponse);
    }

    public boolean deleteProduct(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setActive(false);
                    productRepository.save(product);
                    return true;
                }).orElse(false);
    }

    public List<ProductResponse> searchProducts(@RequestParam String keyword) {
        return productRepository.searchProducts(keyword)
                .stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }
    public void updateProductFromRequest(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setImageUrl(productRequest.getImageUrl());
        product.setCategory(productRequest.getCategory());
        product.setStockQuantity(productRequest.getStockQuantity());

    }

    public ProductResponse mapToProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setName(product.getName());
        productResponse.setId(product.getId());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setImageUrl(product.getImageUrl());
        productResponse.setCategory(product.getCategory());
        productResponse.setStockQuantity(product.getStockQuantity());
        return productResponse;
    }
}
