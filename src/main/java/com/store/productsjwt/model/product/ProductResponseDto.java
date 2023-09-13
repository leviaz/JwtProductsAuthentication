package com.store.productsjwt.model.product;

public record ProductResponseDto(String id, String name, Integer price) {
  public ProductResponseDto(Product product) {
    this(product.getId(), product.getName(), product.getPrice());
  }
}
