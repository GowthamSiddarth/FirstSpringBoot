package com.gowtham.fsp.controller;

import com.gowtham.fsp.exception.ProductAlreadyExistsException;
import com.gowtham.fsp.exception.ProductNotFoundException;
import com.gowtham.fsp.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
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

    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        if (!productRepo.containsKey(id)) {
            throw new ProductNotFoundException();
        }

        productRepo.remove(id);
        return new ResponseEntity<>("Product is Deleted", HttpStatus.OK);
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@PathVariable("id") String id, @RequestBody Product product) {
        if (!productRepo.containsKey(id)) {
            throw new ProductNotFoundException();
        }

        productRepo.remove(id);
        product.setId(id);
        productRepo.put(id, product);
        return new ResponseEntity<>("Product is Updated", HttpStatus.OK);
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody Product product) {
        if (productRepo.containsKey(product.getId())) {
            throw new ProductAlreadyExistsException();
        }
        productRepo.put(product.getId(), product);
        return new ResponseEntity<>("Product is Created", HttpStatus.OK);
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getProducts(@PathVariable("id") String id) {
        if (!productRepo.containsKey(id) && !"all".equals(id)) {
            throw new ProductNotFoundException();
        }

        return new ResponseEntity<>("all".equals(id) ? productRepo.values() : productRepo.get(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/products/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> fileUpload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        File file = new File("D:/IntelliJProjects/fsp/src/main/resources/" + multipartFile.getOriginalFilename());
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(multipartFile.getBytes());
        fileOutputStream.close();
        return new ResponseEntity<>("File Uploaded", HttpStatus.OK);
    }
}
