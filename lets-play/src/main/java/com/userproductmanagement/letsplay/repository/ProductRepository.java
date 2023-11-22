package com.userproductmanagement.letsplay.repository;

import com.userproductmanagement.letsplay.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {


    Optional<Product> findByUserId(String userId);
}
