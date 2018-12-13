package com.gowtham.fsp.controller;

import com.gowtham.fsp.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;

@RestController
public class ConsumeWebService {
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/v1/products", method = RequestMethod.POST)
    public String createProduct(@RequestBody Product product) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Product> entity = new HttpEntity<>(product, httpHeaders);

        try {
            return restTemplate.exchange(
                    "http://localhost:9999/products", HttpMethod.POST, entity, String.class).getBody();
        } catch (HttpClientErrorException ex) {
            return ex.getResponseBodyAsString();
        }
    }

    @RequestMapping(value = "/v1/products/{id}")
    public String getProducts(@PathVariable("id") String id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        try {
            return restTemplate.exchange(
                    "http://localhost:9999/products/" + id, HttpMethod.GET, entity, String.class).getBody();
        } catch (HttpClientErrorException ex) {
            return ex.getResponseBodyAsString();
        }

    }

    @RequestMapping(value = "/v1/products/{id}", method = RequestMethod.PUT)
    public String updateProduct(@PathVariable("id") String id, @RequestBody Product product) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Product> entity = new HttpEntity<>(product, httpHeaders);

        try {
            return restTemplate.exchange(
                    "http://localhost:9999/products/" + id, HttpMethod.PUT, entity, String.class).getBody();
        } catch (HttpClientErrorException ex) {
            return ex.getResponseBodyAsString();
        }
    }

    @RequestMapping(value = "/v1/products/{id}", method = RequestMethod.DELETE)
    public String deleteProduct(@PathVariable("id") String id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        try {
            return restTemplate.exchange(
                    "http://localhost:9999/products/" + id, HttpMethod.DELETE, entity, String.class).getBody();
        } catch (HttpClientErrorException ex) {
            return ex.getResponseBodyAsString();
        }
    }

    @RequestMapping(value = "/v1/products/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setAccept(Arrays.asList(MediaType.MULTIPART_FORM_DATA));
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            File tempFile = new File(new File(System.getProperty("java.io.tmpdir")), multipartFile.getOriginalFilename());
            tempFile.createNewFile();
            body.add("file", new FileSystemResource(tempFile));
            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, httpHeaders);

            return restTemplate.exchange(
                    "http://localhost:9999/products/upload", HttpMethod.POST, entity, String.class).getBody();
        } catch (HttpClientErrorException ex) {
            ex.printStackTrace();
            return ex.getResponseBodyAsString();
        } catch (IOException ex) {
            return ex.getMessage();
        }
    }
}
