package com.gowtham.fsp.service;

import com.gowtham.fsp.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface ProductService {
    ResponseEntity<Object> createProduct(Product product);
    ResponseEntity<Object> getProduct(String id);
    ResponseEntity<Object> updateProduct(String id, Product product);
    ResponseEntity<Object> deleteProduct(String id);
    ResponseEntity<Object> uploadFile(MultipartFile multipartFile) throws IOException;
    ResponseEntity<Object> downloadFile(Map<String, Object> payload);
}
