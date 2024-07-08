package com.testcode.study.testCodeStudy.domain.order;

import com.testcode.study.testCodeStudy.domain.product.Product;
import com.testcode.study.testCodeStudy.domain.product.ProductRepository;
import com.testcode.study.testCodeStudy.domain.product.ProductSellingStatus;
import com.testcode.study.testCodeStudy.domain.product.ProductType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@ActiveProfiles("test")
@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @DisplayName("특정 날짜에 결제 완료된 주문을 조회할 수 있다.")
    @Test
    void findOrdersBy() {
        // given
        Product product1 = createProduct("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", ProductType.HANDMADE, ProductSellingStatus.HOLD, "카페라떼", 4500);
        Product product3 = createProduct("003", ProductType.HANDMADE, ProductSellingStatus.HOLD, "팥빙수", 5000);
        productRepository.saveAll(List.of(product1, product2, product3));

        Order order1 = creatOrderWithBuilder(List.of(product1, product2), OrderStatus.PAYMENT_COMPLETED, LocalDateTime.of(2023, 1, 17, 10, 0));
        orderRepository.save(order1);

        Order order2 = Order.create(List.of(product3), LocalDateTime.of(2023, 1, 18, 16, 0));
        orderRepository.save(order2);

        // when
        List<Order> orders = orderRepository.findOrdersBy(LocalDateTime.of(2023,1,17,10,0), LocalDateTime.of(2023,1,18,17,0),OrderStatus.PAYMENT_COMPLETED);

        // then
        assertThat(orders).hasSize(1)
                .extracting("registeredDateTime", "totalPrice")
                .containsExactlyInAnyOrder(
                        tuple(LocalDateTime.of(2023, 1, 17, 10, 0), 8500)
                );

    }

    private static Order creatOrderWithBuilder(List<Product> products, OrderStatus orderStatus, LocalDateTime localDateTime) {
        return Order.builder()
                .products(products)
                .orderStatus(orderStatus)
                .registeredDateTime(localDateTime)
                .build();
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