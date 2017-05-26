package com.eCheckoutRuler.rules;

import java.util.HashMap;
import java.util.Map;

import com.eCheckoutRuler.ProductItem;
import com.eCheckoutRuler.specialprice.SpecialPrice;

public class Buy2EqualsSpecialPriceDiscount implements Discount {

	private SpecialPrice specialPrice;
	private Map<Integer, Integer> groupedItems;

	public Buy2EqualsSpecialPriceDiscount(SpecialPrice specialPrice) {
		this.specialPrice = specialPrice;

		groupedItems = new HashMap<>();
	}

	@Override
	public void apply(ProductItem item) {
		if (groupedItems.get(item.getId()) == null) {
			groupedItems.put(item.getId(), 0);
		}

		Integer numGroupedItems = groupedItems.get(item.getId()) + 1;

		if (numGroupedItems % 2 == 0) {
			specialPrice.applySpecialPrice(item);
		}

		groupedItems.put(item.getId(), numGroupedItems);
	}

}
