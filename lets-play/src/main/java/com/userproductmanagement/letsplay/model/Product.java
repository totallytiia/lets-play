package com.userproductmanagement.letsplay.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Product's name can't be blank")
    private String name;
    @NotNull(message = "Product's description can't be null")
    @NotBlank(message = "Product's description can't be blank")
    private String description;

    @NotNull(message = "Product's price can't be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price must be 0.0 or more")
    private Double price;

    @NotNull(message = "Requires a user ID")
    private String userId;

}
