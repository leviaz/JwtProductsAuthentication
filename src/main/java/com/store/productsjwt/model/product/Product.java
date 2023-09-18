package com.store.productsjwt.model.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "product")
@Entity(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private String name;
  private Integer price;

  public Product(ProductRequestDto data) {
    this.price = data.price();
    this.name = data.name();
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

}
