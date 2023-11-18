package com.userproductmanagement.letsplay.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "products")
public class Product {
    @Id
    private String id;

    @NotNull(message = "Product's name can't be null")
    private String name;
    @NotNull(message = "Product's description can't be null")
    private String description;

    @NotNull(message = "Product's price can't be null")
    private Double price;

    @NotNull(message = "Requires a user ID")
    private String userId;

}
