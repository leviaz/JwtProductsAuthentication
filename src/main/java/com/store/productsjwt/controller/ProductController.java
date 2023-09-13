package com.store.productsjwt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.productsjwt.model.product.Product;
import com.store.productsjwt.model.product.ProductRequestDto;
import com.store.productsjwt.model.product.ProductResponseDto;
import com.store.productsjwt.repository.ProductRepository;

@RestController
@RequestMapping("/product")
public class ProductController {

  @Autowired
  public ProductRepository repository;

  @PostMapping
  public ResponseEntity<ProductResponseDto> addProduct(@RequestBody ProductRequestDto body) {
    Product product = new Product(body);
    repository.save(product);
    // ProductResponseDto response = new ProductResponseDto(product);
    // return new ResponseEntity<ProductResponseDto>(response, HttpStatus.CREATED);
    return ResponseEntity.ok().build();
  }

  @GetMapping
  public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
    List<ProductResponseDto> productList = this.repository.findAll().stream().map(ProductResponseDto::new).toList();

    return ResponseEntity.ok(productList);
  }

}
