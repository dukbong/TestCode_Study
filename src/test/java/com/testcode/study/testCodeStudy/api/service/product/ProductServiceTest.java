package com.testcode.study.testCodeStudy.api.service.product;

import com.testcode.study.testCodeStudy.api.controller.product.dto.request.ProductCreateRequest;
import com.testcode.study.testCodeStudy.api.service.product.response.ProductResponse;
import com.testcode.study.testCodeStudy.domain.product.Product;
import com.testcode.study.testCodeStudy.domain.product.ProductRepository;
import com.testcode.study.testCodeStudy.domain.product.ProductSellingStatus;
import com.testcode.study.testCodeStudy.domain.product.ProductType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("신규 상품을 등록한다. 상품번호는 가장 최근 상품의 상품번호 + 1 이다.")
    @Test
    void test() {
        // given
        Product product1 = createProduct("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "아메리카노", 3000 );
        productRepository.save(product1);
        ProductCreateRequest request = ProductCreateRequest.builder()
                                                        .type(ProductType.HANDMADE)
                                                        .sellingStatus(ProductSellingStatus.SELLING)
                                                        .name("카푸치노")
                                                        .price(5000)
                                                        .build();
        // when
        ProductResponse productResponse = productService.createProduct(request);

        //then
        assertThat(productResponse)
                .extracting("productNumber", "type", "sellingStatus", "name", "price")
                .contains("002", ProductType.HANDMADE, ProductSellingStatus.SELLING, "카푸치노", 5000);
    }

    private Product createProduct(String productNumber, ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .type(type)
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }

}