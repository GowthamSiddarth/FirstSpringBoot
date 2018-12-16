package com.gowtham.fsp.controller;

import com.gowtham.fsp.model.Product;
import com.gowtham.fsp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@RestController
public class ProductServiceController {
    @Autowired
    ProductService productService;

    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteProduct(@PathVariable("id") String id) {
        return productService.deleteProduct(id);
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateProduct(@PathVariable("id") String id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity<Object> createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getProducts(@PathVariable("id") String id) {
        return productService.getProduct(id);
    }

    @RequestMapping(value = "/products/file/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        File file = new File("D:/IntelliJProjects/fsp/src/main/resources/" + multipartFile.getOriginalFilename());
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(multipartFile.getBytes());
        fileOutputStream.close();
        return new ResponseEntity<>("File Uploaded", HttpStatus.OK);
    }

    @RequestMapping(value = "/products/file/download", method = RequestMethod.POST)
    public ResponseEntity<Object> downloadFile(@RequestBody Map<String, Object> payload) throws IOException {
        System.out.println(payload.get("filename"));
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
