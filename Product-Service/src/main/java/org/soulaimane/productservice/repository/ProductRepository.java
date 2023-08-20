package org.soulaimane.productservice.repository;

import org.soulaimane.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product,String> {
    List<Product> getProductsByName(String name);
}
