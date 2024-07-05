package com.testcode.study.testCodeStudy.api.service.product;

import com.testcode.study.testCodeStudy.api.controller.product.dto.request.ProductCreateRequest;
import com.testcode.study.testCodeStudy.api.service.product.response.ProductResponse;
import com.testcode.study.testCodeStudy.domain.product.Product;
import com.testcode.study.testCodeStudy.domain.product.ProductRepository;
import com.testcode.study.testCodeStudy.domain.product.ProductSellingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Business Layer
 *
 * 1. 비즈니스 로직을 구현하는 역할
 * 2. Persistence Layer와의 상호작용(Data를 읽고 쓰는 행위)을 통해 Business Logic을 전개시킨다.
 * 3. ⭐트랜젝션을 보장해야한다.⭐
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getSellingProducts() {
        List<Product> productList = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisPlay());

        return productList.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public ProductResponse createProduct(ProductCreateRequest request) {
        String nextProductNumber = createNextProductNumber();
        return ProductResponse.builder()
                .productNumber(nextProductNumber)
                .type(request.getType())
                .sellingStatus(request.getSellingStatus())
                .name(request.getName())
                .price(request.getPrice())
                .build();
    }

    private String createNextProductNumber() {
        String latestProductNumber = productRepository.findLatestProductNumber();

        int latestProductNumberInt = Integer.parseInt(latestProductNumber);
        int nextProductNumberInt = latestProductNumberInt + 1;

        return String.format("%03d", nextProductNumberInt);

    }
}
