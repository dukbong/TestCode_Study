package com.testcode.study.testCodeStudy.domain.order;

import com.testcode.study.testCodeStudy.domain.product.Product;
import com.testcode.study.testCodeStudy.domain.product.ProductType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @DisplayName("주문 생성시 상품 리스트에서 주문의 총 금액을 계산한다.")
    @Test
    void calculateTotalPrice() {
        // given
        List<Product> products = List.of(
                createProduct("001", "아메리카노", 4000),
                createProduct("002", "카페라떼", 4500)
        );

        // when
        Order order = Order.create(products, LocalDateTime.now());

        // then
        assertThat(order.getTotalPrice()).isEqualTo(8500);
    }

    @DisplayName("주문 생성시 주문 상태는 INIT이다.")
    @Test
    void init() {
        // given
        List<Product> products = List.of(
                createProduct("001", "아메리카노", 4000),
                createProduct("002", "카페라떼", 4500)
        );

        // when
        Order order = Order.create(products, LocalDateTime.now());

        // then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.INIT);
    }

    @DisplayName("주문 생성 시 등록 시간을 기록한다.")
    @Test
    void registeredDateTime () {
        // given
        List<Product> products = List.of(
                createProduct("001", "아메리카노", 4000),
                createProduct("002", "카페라떼", 4500)
        );

        LocalDateTime registeredDateTime = LocalDateTime.now();
        // when
        Order order = Order.create(products, registeredDateTime);

        // then
        assertThat(order.getRegisteredDateTime()).isEqualTo(registeredDateTime);
    }

    private Product createProduct(String productNumber, String productName, int price){
        return Product.builder()
                .type(ProductType.HANDMADE)
                .name(productName)
                .productNumber(productNumber)
                .price(price)
                .build();
    }
}
