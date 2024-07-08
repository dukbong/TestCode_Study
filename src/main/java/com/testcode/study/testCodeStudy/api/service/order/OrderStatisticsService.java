package com.testcode.study.testCodeStudy.api.service.order;

import com.testcode.study.testCodeStudy.api.service.mail.MailService;
import com.testcode.study.testCodeStudy.domain.order.Order;
import com.testcode.study.testCodeStudy.domain.order.OrderRepository;
import com.testcode.study.testCodeStudy.domain.order.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/***
 * 외부 서비스와 통신하는 경우 Transactional 설정을 하지 않는 것이 좋다.
 */
@Service
@RequiredArgsConstructor
public class OrderStatisticsService {

    private final OrderRepository orderRepository;

    private final MailService mailService;

    public boolean sendOrderStatisticsMail(LocalDate orderDate, String email) {
            // 해당 일자에 결제완료된 주문을 가져와서
            List<Order> orders = orderRepository.findOrdersBy(
                    orderDate.atStartOfDay(),
                    orderDate.plusDays(1).atStartOfDay(),
                    OrderStatus.PAYMENT_COMPLETED
        );

        // 총 매출 합계를 계산하고
        int totalAmount = orders.stream()
                .mapToInt(Order::getTotalPrice)
                .sum();

        // 메일 전송 ( 외부 서비스 )
        boolean result = mailService.sendMail(
                "no-reply@cafeKiosk.com",
                email,
                String.format("[매출 통계] %s", orderDate),
                String.format("총 매출 합계는 %s원 입니다.",totalAmount)
        );

        if(!result) {
            throw new IllegalArgumentException("매출 통계 메일 전송에 실패하였습니다.");
        }

        return true;
    }
}
