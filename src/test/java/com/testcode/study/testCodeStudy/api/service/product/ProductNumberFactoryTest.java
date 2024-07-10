package com.testcode.study.testCodeStudy.api.service.product;

import com.testcode.study.testCodeStudy.domain.product.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ProductNumberFactoryTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductNumberFactory productNumberFactory;

    @DisplayName("상품이 없다면 '001'로 상품 번호가 시작한다.")
    @Test
    void createNextProductNumberWithEmpty() {
        // given
        BDDMockito.given(productRepository.findLatestProductNumber())
                .willReturn(null);

        // when
        String result = productNumberFactory.createNextProductNumber();

        // then
        assertThat(result).isEqualTo("001");
    }

    @DisplayName("다음 상품 번호를 만든다.")
    @Test
    void createNextProductNumber() {
        // given
        BDDMockito.given(productRepository.findLatestProductNumber())
                .willReturn("001");

        // when
        String result = productNumberFactory.createNextProductNumber();

        // then
        assertThat(result).isEqualTo("002");
    }
}