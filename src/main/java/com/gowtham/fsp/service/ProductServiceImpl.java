package com.gowtham.fsp.service;

import com.gowtham.fsp.model.Product;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ProductServiceImpl implements ProductService {
    private static Map<String, Product> productRepo = new HashMap<>();

    static {
        Product honey = new Product();
        honey.setId("1");
        honey.setName("Honey");
        productRepo.put(honey.getId(), honey);

        Product almond = new Product();
        almond.setId("2");
        almond.setName("Almond");
        productRepo.put(almond.getId(), almond);
    }

    @Override
    public ResponseEntity<Object> createProduct(Product product) {
        return null;
    }

    @Override
    public ResponseEntity<Object> getProduct(String id) {
        return null;
    }

    @Override
    public ResponseEntity<Object> updateProduct(String id, Product product) {
        return null;
    }

    @Override
    public ResponseEntity<Object> deleteProduct(String id) {
        return null;
    }
}
