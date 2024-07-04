package com.testcode.study.testCodeStudy.api.service.product;

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
}
