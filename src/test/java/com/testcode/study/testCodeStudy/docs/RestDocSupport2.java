package com.testcode.study.testCodeStudy.docs;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testcode.study.testCodeStudy.api.service.order.OrderService;
import com.testcode.study.testCodeStudy.api.service.product.ProductService;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest
public abstract class RestDocSupport2 {

	@Autowired
	protected MockMvc mockMvc;
	@Autowired
	protected ObjectMapper objectMapper = new ObjectMapper();
	@MockBean
    protected ProductService productService;
	@MockBean
    protected OrderService orderService;

	@BeforeEach
	void setup(WebApplicationContext webApplicationContext, RestDocumentationContextProvider provider) {
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext)
				.apply(documentationConfiguration(provider))
				.build();
	}

}
