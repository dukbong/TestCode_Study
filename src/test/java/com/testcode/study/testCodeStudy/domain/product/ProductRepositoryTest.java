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
        Product product1 = createProduct("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "아메리카노", 3000 );
        Product product2 = createProduct("002", ProductType.HANDMADE, ProductSellingStatus.HOLD, "카페라떼", 4500 );
        Product product3 = createProduct("003", ProductType.HANDMADE, ProductSellingStatus.STOP_SELLING, "자몽허니블랙티", 5900 );

        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        List<Product> products = productRepository.findAllBySellingStatusIn(List.of(ProductSellingStatus.SELLING, ProductSellingStatus.HOLD));

        // then
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "sellingStatus") // 검증 하고자 하는 것을 추출할 수 있다.
                // .containsExactly() 순서까지 보장
                .containsExactlyInAnyOrder( // 순서 상관 없음
                        tuple("001", "아메리카노", ProductSellingStatus.SELLING),
                        tuple("002", "카페라떼", ProductSellingStatus.HOLD)
                );
    }

    @Test
    @DisplayName("원하는 상품번호를 가진 상품을 조회한다.")
    void findAllByProductNumberIn() {
        // given
        Product product1 = createProduct("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "아메리카노", 3000 );
        Product product2 = createProduct("002", ProductType.HANDMADE, ProductSellingStatus.HOLD, "카페라떼", 4500 );
        Product product3 = createProduct("003", ProductType.HANDMADE, ProductSellingStatus.STOP_SELLING, "자몽허니블랙티", 5900 );

        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        List<Product> products = productRepository.findAllByProductNumberIn(List.of("001", "002"));

        // then
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "sellingStatus") // 검증 하고자 하는 것을 추출할 수 있다.
                // .containsExactly() 순서까지 보장
                .containsExactlyInAnyOrder( // 순서 상관 없음
                        tuple("001", "아메리카노", ProductSellingStatus.SELLING),
                        tuple("002", "카페라떼", ProductSellingStatus.HOLD)
                );
    }

    @Test
    @DisplayName("가장 마지막으로 저장한 상품의 상품 번호를 가져온다.")
    void findLatestProductNumber() {
        // given
        String targetProductNumber = "003";
        Product product1 = createProduct("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "아메리카노", 3000 );
        Product product2 = createProduct("002", ProductType.HANDMADE, ProductSellingStatus.HOLD, "카페라떼", 4500 );
        Product product3 = createProduct(targetProductNumber, ProductType.HANDMADE, ProductSellingStatus.STOP_SELLING, "팥빙수", 5000 );
        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        String latestProductNumber = productRepository.findLatestProductNumber();

        // then
        assertThat(latestProductNumber).isEqualTo(targetProductNumber);
    }

    @Test
    @DisplayName("가장 마지막으로 저장한 상품의 상품 번호를 가져올때, 상품이 없다면 null을 반환한다.")
    void findLatestProductNumberNull() {
        // given

        // when
        String latestProductNumber = productRepository.findLatestProductNumber();

        // then
        assertThat(latestProductNumber).isNull();
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