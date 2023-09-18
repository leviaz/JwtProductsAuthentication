package com.store.productsjwt.controller;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.productsjwt.model.product.Product;
import com.store.productsjwt.model.product.ProductRequestDto;
import com.store.productsjwt.model.product.ProductResponseDto;
import com.store.productsjwt.repository.ProductRepository;

@RestController()
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

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteProduct(@PathVariable String id) {
    repository.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductResponseDto> updateProduct(@RequestBody ProductRequestDto productRequestDto,
      @PathVariable String id) {
    Optional<Product> prodOpt = repository.findById(id);
    if (prodOpt.isEmpty()) {
      throw new InputMismatchException("Produto com id inválido");
      // return new ResponseEntity<ProductResponseDto>(HttpStatus.BAD_REQUEST);
    }
    Product product = prodOpt.get();
    product.setId(id);
    product.setName(productRequestDto.name());
    product.setPrice(productRequestDto.price());
    repository.save(product);
    ProductResponseDto dtoResp = new ProductResponseDto(product);
    return new ResponseEntity<>(dtoResp, HttpStatus.ACCEPTED);
  }

}
