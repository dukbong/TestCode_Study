package com.testcode.study.testCodeStudy.api.controller.product;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testcode.study.testCodeStudy.api.controller.product.dto.request.ProductCreateRequest;
import com.testcode.study.testCodeStudy.api.service.product.ProductService;
import com.testcode.study.testCodeStudy.api.service.product.response.ProductResponse;
import com.testcode.study.testCodeStudy.domain.product.ProductSellingStatus;
import com.testcode.study.testCodeStudy.domain.product.ProductType;

@WebMvcTest(controllers = ProductController.class)
// Controller 관련 빈만 로드 하는 가벼운 환경
// Controller 테스트에서 가장 중요한것은 파라미터 유효성 검사를 하는 것이다.
// --> Spring Bean Validation
public class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ProductService productService;

	@DisplayName("신규 상품을 등록한다.")
	@Test
	void createProduct() throws Exception {
		// given
		ProductCreateRequest reqeust = ProductCreateRequest.builder().type(ProductType.HANDMADE)
				.sellingStatus(ProductSellingStatus.SELLING).name("아메리카노").price(4000).build();

		// when & then
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/new")
				.content(objectMapper.writeValueAsString(reqeust))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
    @DisplayName("신규 상품을 등록할 때 상품 타입은 필수값이다.")
    @Test
    void createProductWithoutType() throws Exception {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
            .sellingStatus(ProductSellingStatus.SELLING)
            .name("아메리카노")
            .price(4000)
            .build();

        // when // then
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/products/new")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("상품 타입은 필수입니다."))
            .andExpect(jsonPath("$.data").isEmpty())
        ;
    }
    
    @DisplayName("신규 상품을 등록할 때 상품 판매상태는 필수값이다.")
    @Test
    void createProductWithoutSellingType() throws Exception {
    	// given
    	ProductCreateRequest request = ProductCreateRequest.builder()
    			.type(ProductType.HANDMADE)
    			.name("아메리카노")
    			.price(4000)
    			.build();
    	
    	// when // then
    	mockMvc.perform(
    			MockMvcRequestBuilders.post("/api/v1/products/new")
    			.content(objectMapper.writeValueAsString(request))
    			.contentType(MediaType.APPLICATION_JSON)
    			)
    	.andDo(MockMvcResultHandlers.print())
    	.andExpect(MockMvcResultMatchers.status().isBadRequest())
    	.andExpect(jsonPath("$.code").value("400"))
    	.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
    	.andExpect(jsonPath("$.message").value("상품 판매상태는 필수입니다."))
    	.andExpect(jsonPath("$.data").isEmpty())
    	;
    }
    
    @DisplayName("신규 상품을 등록할 때 상품 이름은 필수값이다.")
    @Test
    void createProductWithoutName() throws Exception {
    	// given
    	ProductCreateRequest request = ProductCreateRequest.builder()
    			.type(ProductType.HANDMADE)
    			.sellingStatus(ProductSellingStatus.SELLING)
    			.price(4000)
    			.build();
    	
    	// when // then
    	mockMvc.perform(
    			MockMvcRequestBuilders.post("/api/v1/products/new")
    			.content(objectMapper.writeValueAsString(request))
    			.contentType(MediaType.APPLICATION_JSON)
    			)
    	.andDo(MockMvcResultHandlers.print())
    	.andExpect(MockMvcResultMatchers.status().isBadRequest())
    	.andExpect(jsonPath("$.code").value("400"))
    	.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
    	.andExpect(jsonPath("$.message").value("상품 이름은 필수입니다."))
    	.andExpect(jsonPath("$.data").isEmpty())
    	;
    }
    
    @DisplayName("신규 상품을 등록할 때 상품 가격은 필수값이다.")
    @Test
    void createProductWithoutPrice() throws Exception {
    	// given
    	ProductCreateRequest request = ProductCreateRequest.builder()
    			.type(ProductType.HANDMADE)
    			.sellingStatus(ProductSellingStatus.SELLING)
    			.name("아메리카노")
    			.build();
    	
    	// when // then
    	mockMvc.perform(
    			MockMvcRequestBuilders.post("/api/v1/products/new")
    			.content(objectMapper.writeValueAsString(request))
    			.contentType(MediaType.APPLICATION_JSON)
    			)
    	.andDo(MockMvcResultHandlers.print())
    	.andExpect(MockMvcResultMatchers.status().isBadRequest())
    	.andExpect(jsonPath("$.code").value("400"))
    	.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
    	.andExpect(jsonPath("$.message").value("상품 가격은 양수여야 합니다."))
    	.andExpect(jsonPath("$.data").isEmpty())
    	;
    }
    
    @DisplayName("판매 상품을 조회한다.")
    @Test
    void getSellingProducts() throws Exception {
    	// given
    	List<ProductResponse> result = List.of();
    	
    	// when 
    	when(productService.getSellingProducts()).thenReturn(result);
    	
    	// then
    	mockMvc.perform(
    			MockMvcRequestBuilders.get("/api/v1/products/selling")
    			)
    	.andDo(MockMvcResultHandlers.print())
    	.andExpect(MockMvcResultMatchers.status().isOk())
    	.andExpect(jsonPath("$.code").value("200"))
    	.andExpect(jsonPath("$.status").value("OK"))
    	.andExpect(jsonPath("$.message").value("OK"))
    	.andExpect(jsonPath("$.data").isArray());
    }
}
