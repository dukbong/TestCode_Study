package com.testcode.study.testCodeStudy.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Persistence Layer의 역할
 *
 * 1. Data Access의 역할
 * 2. 비즈니스 가공 로직이 포함되어서는 안되며 Data에 대한 CURD에만 집중한 레이어
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     * SELECT * FROM PRODUCT WHERE SELLING_STATUS IN ('SELLING', 'HOLD');
     */
    List<Product> findAllBySellingStatusIn(List<ProductSellingStatus> sellingTypes);

    /**
     *  SELECT * FROM PRODUCT WHERE PRODUCT_NUMBER IN ('001', '002');
     */
    List<Product> findAllByProductNumberIn(List<String> productNumbers);
}
