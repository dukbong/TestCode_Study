package com.testcode.study.testCodeStudy.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import org.junit.jupiter.params.provider.Arguments;


class ProductTypeTest {

    @DisplayName("상품 타입이 재고 관련 타입이 아니라면 FALSE")
    @Test
    void containsStockTypeWithHandmade() {
        // given
        ProductType givenType = ProductType.HANDMADE;

        // when
        boolean result = ProductType.containsStockType(givenType);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("상품 타입이 재고 관련 타입이라면 TRUE")
    @Test
    void containsStockTypeWithStockType() {
        // given
        ProductType givenType = ProductType.BAKERY;

        // when
        boolean result = ProductType.containsStockType(givenType);

        // then
        assertThat(result).isTrue();
    }
    
    @DisplayName("상품 타입이 재고 관련 타입인지 체크한다.")
    @Test
    void containsStockType() {
    	// given
    	ProductType givenType1 = ProductType.HANDMADE;
    	ProductType givenType2 = ProductType.BAKERY;
    	ProductType givenType3 = ProductType.BOTTLE;
    	
    	// when
    	boolean result1 = ProductType.containsStockType(givenType1);
    	boolean result2 = ProductType.containsStockType(givenType2);
    	boolean result3 = ProductType.containsStockType(givenType3);
    	
    	// then
    	assertThat(result1).isFalse();
    	assertThat(result2).isTrue();
    	assertThat(result3).isTrue();
    }
    
    // 위 테스트 처럼 하나의 메소드를 테스트하지만 여러개의 변수로 확인하고자 한다면 ParameterizedTest를 사용하여 보기 좋게 만들 수 있다.
    @DisplayName("상품 타입이 재고 관련 타입인지 체크한다.")
    @CsvSource({"HANDMADE, false", "BOTTLE, true", "BAKERY, true"})
    @ParameterizedTest
    void containsStockType2(ProductType productType, boolean expected) {
    	// when
    	boolean result = ProductType.containsStockType(productType);
    	
    	// then
    	assertThat(result).isEqualTo(expected);
    }
    
    
    private static Stream<Arguments> provideProductTypesForCheckingStockType() {
        return Stream.of(
            Arguments.of(ProductType.HANDMADE, false),
            Arguments.of(ProductType.BOTTLE, true),
            Arguments.of(ProductType.BAKERY, true)
        );
    }

    
    @DisplayName("상품 타입이 재고 관련 타입인지 체크한다.")
    @MethodSource("provideProductTypesForCheckingStockType")
    @ParameterizedTest
    void containsStockType4(ProductType productType, boolean expected) {
    	// when
    	boolean result = ProductType.containsStockType(productType);
    	
    	// then
    	assertThat(result).isEqualTo(expected);
    }
    
}