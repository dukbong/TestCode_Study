package com.testcode.study.testCodeStudy.api.service.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.testcode.study.testCodeStudy.api.service.product.request.ProductCreateServiceRequest;
import com.testcode.study.testCodeStudy.api.service.product.response.ProductResponse;
import com.testcode.study.testCodeStudy.domain.product.Product;
import com.testcode.study.testCodeStudy.domain.product.ProductRepository;
import com.testcode.study.testCodeStudy.domain.product.ProductSellingStatus;

import lombok.RequiredArgsConstructor;

/**
 * Business Layer
 *
 * 1. 비즈니스 로직을 구현하는 역할
 * 2. Persistence Layer와의 상호작용(Data를 읽고 쓰는 행위)을 통해 Business Logic을 전개시킨다.
 * 3. ⭐트랜젝션을 보장해야한다.⭐
 */


/**
 * Transactional에 대한 이야기
 * readOnly = true 는 읽기 전용
 * - CRUD 작업 중 CUD 작업은 하지 못한다. [Only Read]
 * - JPA : CUD 스냅샵 저장 및 변경 감지를 할 필요가 없어진다. (성능 향상)
 * ---> Entity를 1차 캐시에 스냅샵처럼 저장 후 커밋 플러시 하는 순간 변경 감지를 하여 업데이트 Query 가 보내지게 된다.
 *
 * - CQRS - Command / Query를 분리하여 서로 책임을 지지 않도록 한다.
 * ---> CUD 작업하는 서비스, R 작업하는 서비스를 분리 한다.
 * ---> DB 앤드 포인트를 분리할 수 있다. (예를 들어 마스터 DB는 CUD, 슬레이브 DB는 R)
 *
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
/**
 * 가장 쉬운 방법 : readOnly = true를 Class 단위에 붙이고 CUD 작업을 하는 Method에 기본 Transactional 붙이기
 * --> 이러면 별도로 Transactional을 달아주지 않으면 모든 Method는 Transactional readOnly = true 상태이다.
 */
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getSellingProducts() {
        List<Product> productList = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisPlay());

        return productList.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    // 동시성 이슈
    // 접속자가 많고 상품 등록을 많이 하면 UUID, 아니라면 DB에서 Unique 제약조건을 걸고 재시도 로직으로 처리
    @Transactional
    public ProductResponse createProduct(ProductCreateServiceRequest request) {
        String nextProductNumber = createNextProductNumber();

        Product product = request.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(product);

        return ProductResponse.of(savedProduct);
    }

    private String createNextProductNumber() {
        String latestProductNumber = productRepository.findLatestProductNumber();

        if(latestProductNumber == null) {
            return "001";
        }

        int latestProductNumberInt = Integer.parseInt(latestProductNumber);
        int nextProductNumberInt = latestProductNumberInt + 1;

        return String.format("%03d", nextProductNumberInt);

    }
}
