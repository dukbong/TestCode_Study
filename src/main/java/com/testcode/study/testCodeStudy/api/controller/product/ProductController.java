package com.testcode.study.testCodeStudy.api.controller.product;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.testcode.study.testCodeStudy.api.ApiResponse;
import com.testcode.study.testCodeStudy.api.controller.product.dto.request.ProductCreateRequest;
import com.testcode.study.testCodeStudy.api.service.product.ProductService;
import com.testcode.study.testCodeStudy.api.service.product.response.ProductResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/api/v1/products/new")
    public ApiResponse<ProductResponse> createProduct(@Valid @RequestBody ProductCreateRequest request) {
    	// @Valid를 붙여서 ProductCreateRequest에서 어노테이션으로 검증하고자 하는 것을 확인한다.
        return ApiResponse.ok(productService.createProduct(request.toServiceRequest()));
    }

    @GetMapping("/api/v1/products/selling")
    public ApiResponse<List<ProductResponse>> getSellingProducts() {
        return ApiResponse.ok(productService.getSellingProducts());
    }

}
