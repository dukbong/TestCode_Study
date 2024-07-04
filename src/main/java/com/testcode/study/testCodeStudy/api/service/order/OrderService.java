package com.testcode.study.testCodeStudy.api.service.order;

import com.testcode.study.testCodeStudy.api.controller.order.request.OrderCreateRequest;
import com.testcode.study.testCodeStudy.api.service.order.response.OrderResponse;
import com.testcode.study.testCodeStudy.domain.order.Order;
import com.testcode.study.testCodeStudy.domain.order.OrderRepository;
import com.testcode.study.testCodeStudy.domain.product.Product;
import com.testcode.study.testCodeStudy.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    // TDD를 사용해서 만들기
    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumbers = request.getProductNumbers();

        // Product
        List<Product> duplicateProducts = findProductsBy(productNumbers);

        // Order
        Order order = Order.create(duplicateProducts, registeredDateTime);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.of(savedOrder);
    }

    private List<Product> findProductsBy(List<String> productNumbers) {
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductNumber, product -> product));

        return productNumbers.stream()
                //.map(productNumber -> productMap.get(productNumber))
                .map(productMap::get)
                .collect(Collectors.toList());
    }

}
