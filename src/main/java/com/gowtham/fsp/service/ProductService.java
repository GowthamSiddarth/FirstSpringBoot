package com.gowtham.fsp.service;

import com.gowtham.fsp.model.Product;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    ResponseEntity<Object> createProduct(Product product);
    ResponseEntity<Object> getProduct(String id);
    ResponseEntity<Object> updateProduct(String id, Product product);
    ResponseEntity<Object> deleteProduct(String id);
}
