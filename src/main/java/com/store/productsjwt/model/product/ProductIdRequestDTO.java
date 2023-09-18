package com.store.productsjwt.model.product;

import jakarta.validation.constraints.NotBlank;

public record ProductIdRequestDTO(@NotBlank String id) {

}
