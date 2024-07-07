package com.testcode.study.testCodeStudy.api.controller.order.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import com.testcode.study.testCodeStudy.api.service.order.request.OrderCreateServiceRequest;

import jakarta.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class OrderCreateRequest {

	@NotEmpty(message = "상품 번호 리스트는 필수입니다.")
    private List<String> productNumbers;

    @Builder
    public OrderCreateRequest(List<String> productNumbers) {
        this.productNumbers = productNumbers;
    }
    
    public OrderCreateServiceRequest toServiceRequest() {
    	return OrderCreateServiceRequest.builder()
    			.productNumbers(productNumbers)
    			.build();
    }
}
