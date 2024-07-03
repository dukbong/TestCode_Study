package com.testcode.study.testCodeStudy.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.testcode.study.testCodeStudy.unit.beverage.Americano;
import com.testcode.study.testCodeStudy.unit.beverage.Latte;
import com.testcode.study.testCodeStudy.unit.order.Order;

public class CafeKioskTest {

	@Test
	void addSingleBeverageManualTest() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		cafeKiosk.add(new Americano());
		
		System.out.println("담긴 음료수 : " + cafeKiosk.getBeverages().size());
		System.out.println("담긴 음료  : " + cafeKiosk.getBeverages().get(0).getName());
	}
	
	
	@Test
	void addSingleBeverage() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		cafeKiosk.add(new Americano());
		
		assertThat(cafeKiosk.getBeverages()).hasSize(1);
		assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
	}
	
	@Test /* HAPPY CASE */
	void addMultipleBeverages() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		
		cafeKiosk.add(americano, 2);
		
		assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
		assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(americano);
	}
	
	@Test /* EXCEPTION CASE */
	void addZeroBeveragesThrowsException() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		
		assertThatThrownBy(() -> cafeKiosk.add(americano, 0))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("음료는 1잔 이상 주문 하실 수 있습니다.");
	}
	
	@Test
	void removeBeverage() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		
		cafeKiosk.add(americano);
		assertThat(cafeKiosk.getBeverages()).hasSize(1);
		
		cafeKiosk.remove(americano);
		assertThat(cafeKiosk.getBeverages()).hasSize(0);
	}
	
	@Test
	void calculateTotalPrice() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		Latte latte = new Latte();
		
		cafeKiosk.add(americano);
		cafeKiosk.add(latte);
		
		int totalPrice = cafeKiosk.calculateTotalPrice();
		
		assertThat(totalPrice).isEqualTo(7500);
	}

/*
	@Test *//* 테스트 하기 어려운 영역 분리하기 [기존] *//*
	void createOrderWithinShopOpenHours() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		
		cafeKiosk.add(americano);
		
		Order order = cafeKiosk.createOrder();
		*//* 만약 테스트 시간이 SHOP_OPEN_TIME과 SHOP_CLOSE_TIME을 벗어나고 있다면 이는 실패하는 케이스가 된다. *//*
		
		assertThat(order.getBeverages()).hasSize(1);
		assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
	}
*/

	
	@Test /* 테스트 하기 어려운 영역 분리하기 [변경] */
	void createOrderAtOpeningTime() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		
		cafeKiosk.add(americano);
		
		Order order = cafeKiosk.createOrder(LocalDateTime.of(2023, 1, 17, 10, 0)); // 경계값 10시
		
		assertThat(order.getBeverages()).hasSize(1);
		assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
	}

	@Test /* 테스트 하기 어려운 영역 분리하기 [변경] */
	@DisplayName("영업 시간 이전에는 주문을 생성할 수 없습니다.")
	void throwExceptionWhenCreatingOrderBeforeOpeningTime() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();

		cafeKiosk.add(americano);

		assertThatThrownBy(() -> cafeKiosk.createOrder(LocalDateTime.of(2023, 1, 17, 9, 0)))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("주문 시간이 아닙니다. 관리자에게 문의하세요.");
	}

	@Test /* 테스트 하기 어려운 영역 분리하기 [변경] */
	void createOrderAtClosingTime() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		
		cafeKiosk.add(americano);
		
		Order order = cafeKiosk.createOrder(LocalDateTime.of(2023, 1, 17, 22, 0)); // 경계값 22시
		
		assertThat(order.getBeverages()).hasSize(1);
		assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
	}

	@Test /* 테스트 하기 어려운 영역 분리하기 [변경] */
	@DisplayName("영업 시간 이후에는 주문을 생성할 수 없습니다.")
	void throwExceptionWhenCreatingOrderAfterClosingTime() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();

		cafeKiosk.add(americano);

		assertThatThrownBy(() -> cafeKiosk.createOrder(LocalDateTime.of(2023, 1, 17, 23, 0)))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("주문 시간이 아닙니다. 관리자에게 문의하세요.");
	}
}
