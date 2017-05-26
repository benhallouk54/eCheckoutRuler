package com.eCheckoutRuler;

import java.util.ArrayList;
import java.util.List;

public class Receipt {

	private List<ProductItem> items;

	public Receipt() {
		items = new ArrayList<>();
	}

	public void addItem(ProductItem item) {
		items.add(item);
	}

	public ProductItem getItem(int i) {
		return items.get(i);
	}

	public double getTotal() {
		double total = 0L;

		for (ProductItem item : items) {
			total += item.getPrice();
		}

		return total;
	}
}
