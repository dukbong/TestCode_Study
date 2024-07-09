package com.testcode.study.testCodeStudy.api.service.order;

import com.testcode.study.testCodeStudy.IntegrationTestSupport;
import com.testcode.study.testCodeStudy.domain.history.mail.MailSendHistory;
import com.testcode.study.testCodeStudy.domain.history.mail.MailSendHistoryRepository;
import com.testcode.study.testCodeStudy.domain.order.Order;
import com.testcode.study.testCodeStudy.domain.order.OrderRepository;
import com.testcode.study.testCodeStudy.domain.order.OrderStatus;
import com.testcode.study.testCodeStudy.domain.orderproduct.OrderProductRepository;
import com.testcode.study.testCodeStudy.domain.product.Product;
import com.testcode.study.testCodeStudy.domain.product.ProductRepository;
import com.testcode.study.testCodeStudy.domain.product.ProductSellingStatus;
import com.testcode.study.testCodeStudy.domain.product.ProductType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//@SpringBootTest
class OrderStatisticsServiceTest extends IntegrationTestSupport {

    @Autowired
    private OrderStatisticsService orderStatisticsService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private MailSendHistoryRepository mailSendHistoryRepository;

    // MockBean은 다른 환경으로 인식하여 테스트 통합 환경으로 만들어도 새로운 서버가 열린다.
    // 해결 방법 1. IntegrationTestSupport 안에서 선언해준다. [protected] - 다른 테스트 환경에서도 열린다.
    // 해결 방법 1에서 다른 테스트 환경에서도 열리는게 싫다면 MockBean처리 된 테스트를 따로 분리하여 진행하는 방법도 있다.
//    @MockBean
//    private MailSendClient mailSendClient;

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        mailSendHistoryRepository.deleteAllInBatch();
    }

    @DisplayName("결제 완료 주문들을매출 통계 메일을 전송한다.")
    @Test
    void sendOrderStatisticsMail() {
        // given
        LocalDateTime now = LocalDateTime.of(2023,3,5,0,0);

        Product product1 = createProduct("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", ProductType.HANDMADE, ProductSellingStatus.HOLD, "카페라떼", 4500);
        Product product3 = createProduct("003", ProductType.HANDMADE, ProductSellingStatus.HOLD, "팥빙수", 5000);
        List<Product> products = List.of(product1, product2, product3);

        productRepository.saveAll(products);

        Order order1 = craetePaymentCompletedOrder(products, LocalDateTime.of(2023,3,4,23,59, 59));
        Order order2 = craetePaymentCompletedOrder(products, now);
        Order order3 = craetePaymentCompletedOrder(products, LocalDateTime.of(2023,3,6,0,0, 0));
        Order order4 = craetePaymentCompletedOrder(products, LocalDateTime.of(2023,3,5,23,59, 59));

        // 이렇게 행동을 통제하고 결과를 임의로 컨트롤하는것을 stubbing이라고 한다.
        when(mailSendClient.sendMail(any(String.class), any(String.class), any(String.class), any(String.class))).thenReturn(true);

        // when
        boolean result = orderStatisticsService.sendOrderStatisticsMail(LocalDate.of(2023,3, 5), "test@test.com");

        // then
        assertThat(result).isTrue();

        List<MailSendHistory> histories = mailSendHistoryRepository.findAll();
        assertThat(histories).hasSize(1)
                .extracting("content")
                .contains("총 매출 합계는 27000원 입니다.");
    }

    private Order craetePaymentCompletedOrder(List<Product> products, LocalDateTime now) {
        Order order = Order.builder()
                .products(products)
                .orderStatus(OrderStatus.PAYMENT_COMPLETED)
                .registeredDateTime(now)
                .build();
        return orderRepository.save(order);
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