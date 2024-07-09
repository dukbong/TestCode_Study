package com.testcode.study.testCodeStudy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testcode.study.testCodeStudy.api.controller.order.OrderController;
import com.testcode.study.testCodeStudy.api.controller.product.ProductController;
import com.testcode.study.testCodeStudy.api.service.order.OrderService;
import com.testcode.study.testCodeStudy.api.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
        OrderController.class,
        ProductController.class
})
public abstract class ControllerTestSupport {

    // ---- OrderControllerTest
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected OrderService orderService;

    // ---- ProductControllerTest
//    @Autowired
//    protected MockMvc mockMvc;

//    @Autowired
//    protected ObjectMapper objectMapper;

    @MockBean
    protected ProductService productService;

}
