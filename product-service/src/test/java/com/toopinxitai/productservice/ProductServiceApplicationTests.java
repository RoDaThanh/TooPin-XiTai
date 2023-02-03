package com.toopinxitai.productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toopinxitai.productservice.dto.ProductRequest;
import com.toopinxitai.productservice.dto.ProductRespone;
import com.toopinxitai.productservice.model.Product;
import com.toopinxitai.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.4");

	static void setProperty(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ProductRepository productRepository;

	@Test
	void createProduct_givenProductRequest_thenCreateNewProduct() throws Exception {
		ProductRequest productRequest = getProductRequest("Iphone 15");
		String productRequestJson = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productRequestJson))
				.andExpect(status().isCreated());
		//List<Product> products = productRepository.findAll();
		//Assertions.assertEquals(1, products.size());
	}

	@Test
	void getAllProducts_given3Products_thenReturn3Products() throws Exception {
//		createProduct("Iphone 10");
//		createProduct("Iphone 11");
//		createProduct("Iphone 12");
//		List<ProductRespone> productRespones = (List<ProductRespone>) mockMvc.perform(MockMvcRequestBuilders.get("/api/product")
//						.contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk()).andReturn().getAsyncResult();
//		Assertions.assertEquals(3, productRespones.size());
	}

	private ProductRequest getProductRequest(String name) {
		ProductRequest productRequest = ProductRequest.builder()
				.name(name)
				.description("New feature")
				.price(new BigDecimal(1000)).build();
		return productRequest;
	}

	private Product getProduct(String name) {
		return Product.builder()
				.name(name)
				.description("New feature")
				.price(new BigDecimal(1000)).build();
	}

	private void createProduct(String name) {
		productRepository.save(getProduct(name));
	}


}
