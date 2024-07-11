package com.testcode.study.testCodeStudy.docs.product;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.testcode.study.testCodeStudy.api.controller.product.dto.request.ProductCreateRequest;
import com.testcode.study.testCodeStudy.docs.RestDocSupport2;
import com.testcode.study.testCodeStudy.domain.product.ProductSellingStatus;

public class ProductControllerDocsTest2 extends RestDocSupport2 {

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
            .andDo(document("product-create-isEmpty-type",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),

                    requestFields(
                            fieldWithPath("type").type(JsonFieldType.STRING).optional()
                                    .description("상품 타입"),
                            fieldWithPath("sellingStatus").type(JsonFieldType.STRING)
                                    .description("상품 판매 상태"),
                            fieldWithPath("name").type(JsonFieldType.STRING)
                                    .description("상품 이름"),
                            fieldWithPath("price").type(JsonFieldType.NUMBER)
                                    .description("상품 가격")
                    ),
                    responseFields(
                            fieldWithPath("code").type(JsonFieldType.NUMBER)
                                    .description("응답 코드"),
                            fieldWithPath("status").type(JsonFieldType.STRING)
                                    .description("응답 상태"),
                            fieldWithPath("message").type(JsonFieldType.STRING)
                                    .description("응답 메시지"),
                            fieldWithPath("data").type(JsonFieldType.OBJECT)
                            		.optional()
                                    .description("응답 데이터")
                    )
            ));
    }
    
}
