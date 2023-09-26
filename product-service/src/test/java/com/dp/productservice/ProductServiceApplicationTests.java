package com.dp.productservice;

import com.dp.productservice.dto.ProductRequest;
import com.dp.productservice.model.Product;
import com.dp.productservice.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	MockMvc mockMvc;


	@Autowired
	ProductRepository productRepository;

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

	@DynamicPropertySource
	static  void  setProperties(DynamicPropertyRegistry registry){
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}
	@Test
	void shouldCreateProduct() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.
				post("/api/v1/product").
				contentType(MediaType.APPLICATION_JSON).
				content(mapper.writeValueAsString(createProductRequest()))).andExpect(MockMvcResultMatchers.status().isCreated());
        Assertions.assertEquals(productRepository.findAll().size(), 1);
	}

	@Test
	void shouldListProducts() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product").contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
		List<Product> products = productRepository.findAll();
		Assertions.assertEquals(products.size(), 1);
		Assertions.assertEquals("test-desc", products.get(0).getDescription());
		Assertions.assertEquals("test-name", products.get(0).getName());
		Assertions.assertEquals(BigDecimal.valueOf(1200), products.get(0).getPrice());
	}
	private ProductRequest createProductRequest(){
		return ProductRequest.builder().name("test-name").description("test-desc").price(BigDecimal.valueOf(1200)).build();
	}

}
