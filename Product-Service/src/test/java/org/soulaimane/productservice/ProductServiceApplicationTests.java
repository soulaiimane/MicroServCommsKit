package org.soulaimane.productservice;


import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.soulaimane.productservice.dto.ProductRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@AllArgsConstructor
class ProductServiceApplicationTests {
	@Container
	static MongoDBContainer mongoDBContainer=new MongoDBContainer("mongo:6.0.8");
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;
	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		String dockerHubUsername = System.getenv("soulaiimane");
		String dockerHubPassword = System.getenv("makayna m3na");

		if (dockerHubUsername != null && dockerHubPassword != null) {
			dynamicPropertyRegistry.add("testcontainers.images.pull.credentials", () -> Map.of(
					"registry-1.docker.io", Map.of(
							"username", dockerHubUsername,
							"password", dockerHubPassword
					)
			));
		}

		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}
	@Test
	void shouldAddProduct() throws Exception {
		ProductRequestDto productRequestDto=getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequestDto);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productRequestString))
				.andExpect(status().isCreated());


	}

	private ProductRequestDto getProductRequest() {
		return ProductRequestDto.builder()
				.name("hp")
				.price(BigDecimal.valueOf(6384) )
				.description("pc")
				.build();
	}

}
