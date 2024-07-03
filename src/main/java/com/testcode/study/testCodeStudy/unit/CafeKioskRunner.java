package com.testcode.study.testCodeStudy.unit;

import com.testcode.study.testCodeStudy.unit.beverage.Americano;
import com.testcode.study.testCodeStudy.unit.beverage.Latte;

public class CafeKioskRunner {
	public static void main(String[] args) {
		CafeKiosk cafeKiosk = new CafeKiosk();
		cafeKiosk.add(new Americano());
		cafeKiosk.add(new Latte());
		
		int totalPrice = cafeKiosk.calculateTotalPrice();
		
	}
}
