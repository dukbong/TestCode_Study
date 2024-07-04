package com.testcode.study.testCodeStudy.domain.orderproduct;

import com.testcode.study.testCodeStudy.domain.BaseEntity;
import com.testcode.study.testCodeStudy.domain.order.Order;
import com.testcode.study.testCodeStudy.domain.product.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * Order와 Prodcut 사이에 있는 중간 테이블
 * -> N:N 관계 대신 1:N, 1:N 관계로 쪼개기 위함
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    public OrderProduct(Order order, Product product) {
        this.order = order;
        this.product = product;
    }
}
