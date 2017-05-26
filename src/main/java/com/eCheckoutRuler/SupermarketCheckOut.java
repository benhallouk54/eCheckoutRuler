package com.eCheckoutRuler;

import java.util.List;

import com.eCheckoutRuler.rules.Discount;

public class SupermarketCheckOut {

	private List<Discount> rules;

	public SupermarketCheckOut(List<Discount> rules) {
		this.rules = rules;
	}

	public Receipt calculatePrice(List<ProductItem> items) {
		Receipt receipt = new Receipt();

		for (ProductItem item : items) {

			for (Discount rule : rules) {
				rule.apply(item);
			}

			receipt.addItem(item);
		}

		return receipt;
	}

}
