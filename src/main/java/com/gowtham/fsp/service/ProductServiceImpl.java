package com.gowtham.fsp.service;

import com.gowtham.fsp.exception.ProductAlreadyExistsException;
import com.gowtham.fsp.exception.ProductNotFoundException;
import com.gowtham.fsp.model.Product;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
    public ResponseEntity<Object> uploadFile(MultipartFile multipartFile) throws IOException {
        File file = new File("D:/IntelliJProjects/fsp/src/main/resources/" + multipartFile.getOriginalFilename());
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(multipartFile.getBytes());
        fileOutputStream.close();
        return new ResponseEntity<>("File Uploaded", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> downloadFile(Map<String, Object> payload) throws IOException {
        File file = new File("D:/IntelliJProjects/fsp/src/main/resources/" + payload.get("filename"));
        InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", String.format("attachment; filename=\"%s\"", payload.get("filename")));
        httpHeaders.add("Cache-Control", "no-cache, no-store, must-revalidate");
        httpHeaders.add("Pragma", "no-cache");
        httpHeaders.add("Expires", "0");

        return ResponseEntity.ok().headers(httpHeaders).contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/txt")).body(inputStreamResource);
    }
}
