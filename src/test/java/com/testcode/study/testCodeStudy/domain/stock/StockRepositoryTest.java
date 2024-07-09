package com.testcode.study.testCodeStudy.domain.stock;

import com.testcode.study.testCodeStudy.IntegrationTestSupport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

//@DataJpaTest
class StockRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private StockRepository stockRepository;

    @AfterEach
    void tearDown() {
        stockRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("상품 번호 리스트로 재고를 조회한다.")
    void findAllByProductNumberIn() {
        // given
        Stock stock1 = Stock.create("001", 1);
        Stock stock2 = Stock.create("002", 2);
        Stock stock3 = Stock.create("003", 3);
        stockRepository.saveAll(List.of(stock1, stock2, stock3));

        // when
        List<Stock> stocks = stockRepository.findAllByProductNumberIn(List.of("001", "002"));

        // then
        assertThat(stocks).hasSize(2)
                .extracting("productNumber", "quantity") // 검증 하고자 하는 것을 추출할 수 있다.
                // .containsExactly() 순서까지 보장
                .containsExactlyInAnyOrder( // 순서 상관 없음
                        tuple("001", 1),
                        tuple("002", 2)
                );
    }
}