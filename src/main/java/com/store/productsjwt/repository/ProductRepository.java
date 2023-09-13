package com.store.productsjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.productsjwt.model.product.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

}
