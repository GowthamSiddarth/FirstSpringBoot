package com.gowtham.fsp.controller;

import com.gowtham.fsp.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
public class ConsumeWebService {
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/v1/products", method = RequestMethod.POST)
    public String createProduct(@RequestBody Product product) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Product> entity = new HttpEntity<>(product, httpHeaders);

        return restTemplate.exchange(
                "http://localhost:9999/products", HttpMethod.POST, entity, String.class).getBody();
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
}
