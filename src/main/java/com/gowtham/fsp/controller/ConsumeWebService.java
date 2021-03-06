package com.gowtham.fsp.controller;

import com.gowtham.fsp.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

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

    @RequestMapping(value = "/v1/products/file/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setAccept(Arrays.asList(MediaType.MULTIPART_FORM_DATA));

            String tempFileName = System.getProperty("java.io.tmpdir") + multipartFile.getOriginalFilename();
            File tempFile = new File(tempFileName);
            FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
            fileOutputStream.write(multipartFile.getBytes());
            fileOutputStream.close();

            FileSystemResource fileSystemResource = new FileSystemResource(tempFileName);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", fileSystemResource);
            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, httpHeaders);

            return restTemplate.exchange(
                    "http://localhost:9999/products/file/upload", HttpMethod.POST, entity, String.class).getBody();
        } catch (HttpClientErrorException ex) {
            ex.printStackTrace();
            return ex.getResponseBodyAsString();
        } catch (IOException ex) {
            ex.printStackTrace();
            return ex.getMessage();
        }
    }

    @RequestMapping(value = "/v1/products/file/download", method = RequestMethod.POST)
    public byte[] downloadFile(@RequestBody Map<String, Object> body) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, httpHeaders);

        try {
            return restTemplate.exchange(
                    "http://localhost:9999/products/file/download", HttpMethod.POST, entity, byte[].class).getBody();
        } catch (HttpClientErrorException ex) {
            ex.printStackTrace();
            ex.getResponseBodyAsString();
            return "File not found in server".getBytes();
        }
    }
}
