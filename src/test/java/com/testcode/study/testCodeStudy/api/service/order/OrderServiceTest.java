package com.testcode.study.testCodeStudy.api.service.order;

import com.testcode.study.testCodeStudy.api.controller.order.request.OrderCreateRequest;
import com.testcode.study.testCodeStudy.api.service.order.response.OrderResponse;
import com.testcode.study.testCodeStudy.domain.order.OrderRepository;
import com.testcode.study.testCodeStudy.domain.orderproduct.OrderProductRepository;
import com.testcode.study.testCodeStudy.domain.product.Product;
import com.testcode.study.testCodeStudy.domain.product.ProductRepository;
import com.testcode.study.testCodeStudy.domain.product.ProductType;
import com.testcode.study.testCodeStudy.domain.stock.Stock;
import com.testcode.study.testCodeStudy.domain.stock.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
//@Transactional
@SpringBootTest
class OrderServiceTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private OrderService orderService;

    @AfterEach
    void tearDown() {
        // 각 테스트에 영향을 주지 않기 위함
        orderProductRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        stockRepository.deleteAllInBatch();
    }

    @DisplayName("주문 번호 리스트를 받아 주문을 생성한다.")
    @Test
    void createOrder() {
        // given
        Product product1 = createProduct(ProductType.HANDMADE, "001", "아메리카노", 4000);
        Product product2 = createProduct(ProductType.HANDMADE, "002", "카페 라떼", 4500);
        Product product3 = createProduct(ProductType.HANDMADE, "003", "자몽 허니 블랙티", 5900);
        productRepository.saveAll(List.of(product1, product2, product3));

        OrderCreateRequest request = OrderCreateRequest.builder()
                .productNumbers(List.of("001", "002"))
                .build();

        LocalDateTime registeredDateTime = LocalDateTime.now();

        // when
        OrderResponse orderResponse = orderService.createOrder(request.toServiceRequest(), registeredDateTime);

        // then
        assertThat(orderResponse.getId()).isNotNull();
        assertThat(orderResponse)
                .extracting("registeredDateTime", "totalPrice")
                .contains(registeredDateTime, 8500);
        assertThat(orderResponse.getProducts()).hasSize(2)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", 4000),
                        tuple("002", 4500)
                );
    }

    @DisplayName("중복 되는 상품번호 리스트로 주문을 생성할 수 있다.")
    @Test
    void createOrderWithDuplicateProducts() {
        // given
        Product product1 = createProduct(ProductType.HANDMADE, "001", "아메리카노", 4000);
        Product product2 = createProduct(ProductType.HANDMADE, "002", "카페 라떼", 4500);
        Product product3 = createProduct(ProductType.HANDMADE, "003", "자몽 허니 블랙티", 5900);
        productRepository.saveAll(List.of(product1, product2, product3));

        OrderCreateRequest request = OrderCreateRequest.builder()
                .productNumbers(List.of("001", "001"))
                .build();

        LocalDateTime registeredDateTime = LocalDateTime.now();

        // when
        OrderResponse orderResponse = orderService.createOrder(request.toServiceRequest(), registeredDateTime);

        // then
        assertThat(orderResponse.getId()).isNotNull();
        assertThat(orderResponse)
                .extracting("registeredDateTime", "totalPrice")
                .contains(registeredDateTime, 8000);
        assertThat(orderResponse.getProducts()).hasSize(2)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", 4000),
                        tuple("001", 4000)
                );
    }

    @DisplayName("재고와 관련된 상품이 포함되어 있는 주문번호 리스트를 받아 주문을 생성한다.")
    @Test
    void createOrderWithStock() {
        // given
        Product product1 = createProduct(ProductType.BOTTLE, "001", "아메리카노", 1000);
        Product product2 = createProduct(ProductType.BAKERY, "002", "카페 라떼", 3000);
        Product product3 = createProduct(ProductType.HANDMADE, "003", "자몽 허니 블랙티", 5000);
        productRepository.saveAll(List.of(product1, product2, product3));

        Stock stock1 = Stock.create("001", 2);
        Stock stock2 = Stock.create("002", 2);
        stockRepository.saveAll(List.of(stock1, stock2));

        OrderCreateRequest request = OrderCreateRequest.builder()
                .productNumbers(List.of("001", "001", "002", "003"))
                .build();

        LocalDateTime registeredDateTime = LocalDateTime.now();

        // when
        OrderResponse orderResponse = orderService.createOrder(request.toServiceRequest(), registeredDateTime);

        // then
        assertThat(orderResponse.getId()).isNotNull();
        assertThat(orderResponse)
                .extracting("registeredDateTime", "totalPrice")
                .contains(registeredDateTime, 10000);
        assertThat(orderResponse.getProducts()).hasSize(4)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", 1000),
                        tuple("001", 1000),
                        tuple("002", 3000),
                        tuple("003", 5000)
                );
        List<Stock> stocks = stockRepository.findAll();
        assertThat(stocks).hasSize(2)
                .extracting("productNumber", "quantity")
                .containsExactlyInAnyOrder(
                        tuple("001", 0),
                        tuple("002", 1)
                );
    }

    @DisplayName("재고가 부족한 상품으로 주문을 생성하려는 경우 예외가 발생한다.")
    @Test
    void createOrderWithNoStock() {
        // given
        Product product1 = createProduct(ProductType.BOTTLE, "001", "아메리카노", 1000);
        Product product2 = createProduct(ProductType.BAKERY, "002", "카페 라떼", 3000);
        Product product3 = createProduct(ProductType.HANDMADE, "003", "자몽 허니 블랙티", 5000);
        productRepository.saveAll(List.of(product1, product2, product3));

        Stock stock1 = Stock.create("001", 1);
        Stock stock2 = Stock.create("002", 1);
        stockRepository.saveAll(List.of(stock1, stock2));

        OrderCreateRequest request = OrderCreateRequest.builder()
                .productNumbers(List.of("001", "001", "002", "003"))
                .build();

        LocalDateTime registeredDateTime = LocalDateTime.now();

        // when
        assertThatThrownBy(() -> orderService.createOrder(request.toServiceRequest(), registeredDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고가 부족한 상품이 있습니다.");
    }

    private Product createProduct(ProductType type, String productNumber, String productName, int price){
        return Product.builder()
                .type(type)
                .name(productName)
                .productNumber(productNumber)
                .price(price)
                .build();
    }

}