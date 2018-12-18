package com.gowtham.fsp.service;

import com.gowtham.fsp.exception.ProductAlreadyExistsException;
import com.gowtham.fsp.exception.ProductNotFoundException;
import com.gowtham.fsp.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
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
        if (productRepo.containsKey(product.getId())) {
            throw new ProductAlreadyExistsException();
        }
        productRepo.put(product.getId(), product);
        return new ResponseEntity<>("Product is Created", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getProduct(String id) {
        if (!productRepo.containsKey(id) && !"all".equals(id)) {
            throw new ProductNotFoundException();
        }

        return new ResponseEntity<>("all".equals(id) ? productRepo.values() : productRepo.get(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> updateProduct(String id, Product product) {
        if (!productRepo.containsKey(id)) {
            throw new ProductNotFoundException();
        }

        productRepo.remove(id);
        product.setId(id);
        productRepo.put(id, product);
        return new ResponseEntity<>("Product is Updated", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> deleteProduct(String id) {
        if (!productRepo.containsKey(id)) {
            throw new ProductNotFoundException();
        }

        productRepo.remove(id);
        return new ResponseEntity<>("Product is Deleted", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> uploadFile(MultipartFile multipartFile) {
        return null;
    }

    @Override
    public ResponseEntity<Object> downloadFile(Map<String, Object> payload) {
        return null;
    }
}
