package com.testcode.study.testCodeStudy.api.service.product;

import com.testcode.study.testCodeStudy.IntegrationTestSupport;
import com.testcode.study.testCodeStudy.api.controller.product.dto.request.ProductCreateRequest;
import com.testcode.study.testCodeStudy.api.service.product.response.ProductResponse;
import com.testcode.study.testCodeStudy.domain.product.Product;
import com.testcode.study.testCodeStudy.domain.product.ProductRepository;
import com.testcode.study.testCodeStudy.domain.product.ProductSellingStatus;
import com.testcode.study.testCodeStudy.domain.product.ProductType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

//@ActiveProfiles("test")
//@SpringBootTest
class ProductServiceTest extends IntegrationTestSupport {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;
    
   
    // BeforeAll과 BeforeEach는 잘 쓰지 않는다.
    // 테스트 코드는 문서 같은 성격을 가지기 때문에 위에서 부터 차례대로 읽어 나갈 수 있어야 한다.
 //   @BeforeAll
 //   void beforeAll() {
    	// before class
 //   }
 //   @BeforeEach
 //   void setUp() {
    	// before method
    	
    	// 언제 사용할까?
    	// 1. 각 테스트 입장에서 봤을때 아예 몰라도 테스트 내용을 이해하는데 문제가 없는가?
    	// 2. 수정해도 모든 테스트에 영향을 주지 않는가?
 //   }

    @AfterEach
    void tearDown() {
        productRepository.deleteAllInBatch();
    }

    @DisplayName("신규 상품을 등록한다. 상품번호는 가장 최근 상품의 상품번호 + 1 이다.")
    @Test
    void createProduct() {
        // given
        Product product1 = createProduct("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "아메리카노", 3000 );
        productRepository.save(product1);
        ProductCreateRequest request = ProductCreateRequest.builder()
                                                        .type(ProductType.HANDMADE)
                                                        .sellingStatus(ProductSellingStatus.SELLING)
                                                        .name("카푸치노")
                                                        .price(5000)
                                                        .build();
        // when
        ProductResponse productResponse = productService.createProduct(request.toServiceRequest());

        //then
        assertThat(productResponse)
                .extracting("productNumber", "type", "sellingStatus", "name", "price")
                .contains("002", ProductType.HANDMADE, ProductSellingStatus.SELLING, "카푸치노", 5000);

        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(2)
                .extracting("productNumber", "type", "sellingStatus", "name", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "아메리카노", 3000),
                        tuple("002", ProductType.HANDMADE, ProductSellingStatus.SELLING, "카푸치노", 5000)
                );
    }

    @DisplayName("상품이 하나도 없는 경우 신규 상품을 등록하면 상품번호는 001이다.")
    @Test
    void createProductWhenProductIsEmpty() {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                                                        .type(ProductType.HANDMADE)
                                                        .sellingStatus(ProductSellingStatus.SELLING)
                                                        .name("카푸치노")
                                                        .price(5000)
                                                        .build();
        // when
        ProductResponse productResponse = productService.createProduct(request.toServiceRequest());

        //then
        assertThat(productResponse)
                .extracting("productNumber", "type", "sellingStatus", "name", "price")
                .contains("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "카푸치노", 5000);

        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(1)
                .extracting("productNumber", "type", "sellingStatus", "name", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "카푸치노", 5000)
                );
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