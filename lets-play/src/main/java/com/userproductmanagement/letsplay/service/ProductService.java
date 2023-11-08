package com.userproductmanagement.letsplay.service;

import com.userproductmanagement.letsplay.model.Product;

import java.util.List;

public interface ProductService {

    public List<Product> getProducts();

    public Product getProduct(String id);

    public Product addProduct(Product product);

    public Product deleteProduct(String id);

    public Product updateProduct(String id, Product product);
}
