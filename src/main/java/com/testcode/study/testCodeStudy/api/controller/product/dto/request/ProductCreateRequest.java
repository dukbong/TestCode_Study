package com.testcode.study.testCodeStudy.api.controller.product.dto.request;

import com.testcode.study.testCodeStudy.api.service.product.request.ProductCreateServiceRequest;
import com.testcode.study.testCodeStudy.domain.product.Product;
import com.testcode.study.testCodeStudy.domain.product.ProductSellingStatus;
import com.testcode.study.testCodeStudy.domain.product.ProductType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductCreateRequest {
	
	@NotNull(message = "상품 타입은 필수입니다.")
	private ProductType type;
	
	@NotNull(message = "상품 판매상태는 필수입니다.")
    private ProductSellingStatus sellingStatus;
	
	// String 타입을 검증하는데 있어서 헷갈리는게 NotBlank, Notnull, NotEmpty이다.
	// NotNull : Null만 안된다. "", "  " 이것은 통과
	// NotEmpty : Null과 ""이런건 안된다. 하지만 "   " 이건 통과
	// NotBlank : Null, "", "    "이거 모두 안된다.
	@NotBlank(message = "상품 이름은 필수입니다.")
    private String name;
	
	@Positive(message = "상품 가격은 양수여야 합니다.")
    private int price;

    // 현재 해당 Builder는 테스트에서만 사용하는 코드인다.
    @Builder
    public ProductCreateRequest(ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }
    
    public ProductCreateServiceRequest toServiceRequest() {
    	return ProductCreateServiceRequest.builder()
    			.type(type)
    			.sellingStatus(sellingStatus)
    			.name(name)
    			.price(price)
    			.build();
    }
}
