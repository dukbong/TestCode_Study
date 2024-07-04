package com.testcode.study.testCodeStudy.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

/**
 * 해당 TEST Code는 통합 테스트이다.
 */

@ActiveProfiles("test") // application.yml 에서 test 환경으로 돌리겠다고 지정
//// @SpringBootTest
@DataJpaTest // JPA 관련 Bean만 주입하여 SpringBootTest 보다 가볍다.
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("원하는 판매상태를 가진 상품을 조회한다.")
    void findAllBySellingStatusIn() {
        // given
        Product product1 = Product.builder()
                .productNumber("001")
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("아메리카노")
                .price(3000)
                .build();
        Product product2 = Product.builder()
                .productNumber("002")
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.HOLD)
                .name("카페 라떼")
                .price(4500)
                .build();
        Product product3 = Product.builder()
                .productNumber("003")
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.STOP_SELLING)
                .name("자몽 허니 블랙티")
                .price(5900)
                .build();

        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        List<Product> products = productRepository.findAllBySellingStatusIn(List.of(ProductSellingStatus.SELLING, ProductSellingStatus.HOLD));

        // then
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "sellingStatus") // 검증 하고자 하는 것을 추출할 수 있다.
                // .containsExactly() 순서까지 보장
                .containsExactlyInAnyOrder( // 순서 상관 없음
                        tuple("001", "아메리카노", ProductSellingStatus.SELLING),
                        tuple("002", "카페 라떼", ProductSellingStatus.HOLD)
                );
    }

    @Test
    @DisplayName("원하는 상품번호를 가진 상품을 조회한다.")
    void findAllByProductNumberIn() {
        // given
        Product product1 = Product.builder()
                .productNumber("001")
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("아메리카노")
                .price(3000)
                .build();
        Product product2 = Product.builder()
                .productNumber("002")
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.HOLD)
                .name("카페 라떼")
                .price(4500)
                .build();
        Product product3 = Product.builder()
                .productNumber("003")
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.STOP_SELLING)
                .name("자몽 허니 블랙티")
                .price(5900)
                .build();

        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        List<Product> products = productRepository.findAllByProductNumberIn(List.of("001", "002"));

        // then
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "sellingStatus") // 검증 하고자 하는 것을 추출할 수 있다.
                // .containsExactly() 순서까지 보장
                .containsExactlyInAnyOrder( // 순서 상관 없음
                        tuple("001", "아메리카노", ProductSellingStatus.SELLING),
                        tuple("002", "카페 라떼", ProductSellingStatus.HOLD)
                );
    }
}