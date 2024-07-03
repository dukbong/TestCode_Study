package com.testcode.study.testCodeStudy.coffeshop;

import com.testcode.study.testCodeStudy.coffeshop.beverage.Americano;
import com.testcode.study.testCodeStudy.coffeshop.beverage.Latte;

public class CafeKioskRunner {
	public static void main(String[] args) {
		CafeKiosk cafeKiosk = new CafeKiosk();
		cafeKiosk.add(new Americano());
		cafeKiosk.add(new Latte());
		
		int totalPrice = cafeKiosk.calculateTotalPrice();
		
	}
}
