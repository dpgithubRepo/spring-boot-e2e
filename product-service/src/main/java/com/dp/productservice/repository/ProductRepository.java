package com.dp.productservice.repository;

import com.dp.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ProductRepository  extends MongoRepository<Product,String> {
}
