package com.gowtham.fsp.controller;

import com.gowtham.fsp.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ProductServiceController {
    private static Map<String, Product> productRepo = new HashMap<>();

    static {
        Product product1 = new Product();
        product1.setId("1");
        product1.setName("Honey");
        productRepo.put(product1.getId(), product1);

        Product product2 = new Product();
        product2.setId("2");
        product2.setName("Almond");
        productRepo.put(product2.getId(), product2);
    }
}
