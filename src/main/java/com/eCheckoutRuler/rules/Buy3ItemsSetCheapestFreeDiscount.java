package com.eCheckoutRuler.rules;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.eCheckoutRuler.ProductItem;

public class Buy3ItemsSetCheapestFreeDiscount implements Discount {

	private Set<Integer> itemsOnSet;
	private List<Set<ProductItem>> groupedItems;

	public Buy3ItemsSetCheapestFreeDiscount(Set<Integer> itemsOnSet) {
		this.itemsOnSet = itemsOnSet;

		groupedItems = new ArrayList<>();
	}

	@Override
	public void apply(ProductItem item) {
		if (itemsOnSet.contains(item.getId())) {

			if (groupedItems.isEmpty()) {
				createNewGroup(item);
				return;
			}

			boolean isGrouped = false;
			for (Set<ProductItem> group : groupedItems) {
				if (!group.contains(item)) {
					group.add(item);
					isGrouped = true;

					if (group.size() == 3) {
						cheapestItemIsFree(group);
					}

					break;
				}
			}

			if (!isGrouped) {
				createNewGroup(item);
			}
		}
	}

	private void cheapestItemIsFree(Set<ProductItem> group) {
		ProductItem cheapestItem = null;

		for (ProductItem itemGrouped : group) {
			if (cheapestItem == null
					|| (cheapestItem.getPrice() > itemGrouped.getPrice())) {
				cheapestItem = itemGrouped;
			}
		}

		cheapestItem.setPrice(0L);
	}

	private void createNewGroup(ProductItem item) {
		Set<ProductItem> newGroup = new HashSet<>();
		newGroup.add(item);

		groupedItems.add(newGroup);
	}

}
