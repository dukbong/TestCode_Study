package com.testcode.study.testCodeStudy.api.controller.order;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

import com.testcode.study.testCodeStudy.ControllerTestSupport;
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
import com.testcode.study.testCodeStudy.api.controller.order.request.OrderCreateRequest;
import com.testcode.study.testCodeStudy.api.service.order.OrderService;

//@WebMvcTest(controllers = OrderController.class)
public class OrderControllerTest extends ControllerTestSupport {

//	@Autowired
//	private MockMvc mockMvc;
//
//	@Autowired
//	private ObjectMapper objectMapper;
//
//	@MockBean
//	private OrderService orderService;
	
	@DisplayName("신규 주문을 등록한다.")
	@Test
	void createOrder() throws Exception {
		// given
		OrderCreateRequest request = OrderCreateRequest.builder()
				.productNumbers(List.of("001"))
				.build();
		
		// when
		// then
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders/new")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.code").value("200"))
		.andExpect(jsonPath("$.status").value("OK"))
		.andExpect(jsonPath("$.message").value("OK"))
		;
	}
	
	@DisplayName("신규 주문을 등록할때 상품번호는 1개 이상이어야 합니다.")
	@Test
	void createOrderWithEmptyProductNumber() throws Exception {
		// given
		OrderCreateRequest request = OrderCreateRequest.builder()
				.productNumbers(List.of())
				.build();
		
		// when
		// then
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders/new")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andExpect(jsonPath("$.code").value("400"))
		.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
		.andExpect(jsonPath("$.message").value("상품 번호 리스트는 필수입니다."))
		.andExpect(jsonPath("$.data").isEmpty())
		;
	}
}
