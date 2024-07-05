package com.testcode.study.testCodeStudy.api.controller.product;

import com.testcode.study.testCodeStudy.api.controller.product.dto.request.ProductCreateRequest;
import com.testcode.study.testCodeStudy.api.service.product.ProductService;
import com.testcode.study.testCodeStudy.api.service.product.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/api/v1/products/new")
    public void createProduct(@RequestBody ProductCreateRequest request) {
        productService.createProduct(request);
    }

    @GetMapping("/api/v1/products/selling")
    public List<ProductResponse> getSellingProducts() {
        return productService.getSellingProducts();
    }

}
