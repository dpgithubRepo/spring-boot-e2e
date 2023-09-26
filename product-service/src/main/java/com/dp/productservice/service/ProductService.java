package com.dp.productservice.service;


import com.dp.productservice.dto.ProductRequest;
import com.dp.productservice.dto.ProductResponse;
import com.dp.productservice.model.Product;
import com.dp.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);

        log.info("Product Saved {}", product);

    }

    public List<ProductResponse> getAllProducts() {

        List<Product> products =  productRepository.findAll();

        return products.stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }

    private ProductResponse mapToProductResponse(Product product) {

        return ProductResponse.builder().id(product.getId()).name(product.getName()).description(product.getDescription())
                .price(product.getPrice()).build();
    }
}
