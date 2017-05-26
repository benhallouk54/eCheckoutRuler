package com.eCheckoutRuler.rules;

import java.util.HashMap;
import java.util.Map;

import com.eCheckoutRuler.ProductItem;

public class Buy3Equals1FreeDiscount implements Discount {

	private Map<Integer, Integer> groupedItems;

	public Buy3Equals1FreeDiscount() {
		groupedItems = new HashMap<>();
	}

	@Override
	public void apply(ProductItem item) {
		if (groupedItems.get(item.getId()) == null) {
			groupedItems.put(item.getId(), 0);
		}

		Integer numGroupedItems = groupedItems.get(item.getId()) + 1;

		if (numGroupedItems % 3 == 0) {
			item.setPrice(0L);
		}

		groupedItems.put(item.getId(), numGroupedItems);
	}

}
