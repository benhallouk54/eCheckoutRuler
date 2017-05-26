package com.eCheckoutRuler.rules;

import java.util.ArrayList;
import java.util.List;

import com.eCheckoutRuler.ProductItem;

public class BuyNItemsXGetKItemsYFreeDiscount implements Discount {

	private int numItems;
	private int productId;
	private int numItemsFree;
	private int productIdFree;

	private List<List<ProductItem>> items;
	private List<List<ProductItem>> itemsForFree;

	public BuyNItemsXGetKItemsYFreeDiscount(int numItems, int productId,
			int numItemsFree, int productIdFree) {

		this.numItems = numItems;
		this.productId = productId;
		this.numItemsFree = numItemsFree;
		this.productIdFree = productIdFree;

		items = new ArrayList<>();
		items.add(new ArrayList<ProductItem>());

		itemsForFree = new ArrayList<>();
		itemsForFree.add(new ArrayList<ProductItem>());
	}

	@Override
	public void apply(ProductItem item) {
		if (item.getId() == productId) {
			List<ProductItem> group = items.get(items.size() - 1);

			if (group.size() < numItems) {
				group.add(item);

				if (group.size() == numItems && !itemsForFree.isEmpty()) {
					for (ProductItem itemFree : itemsForFree.get(0)) {
						itemFree.setPrice(0L);
					}

					if (itemsForFree.get(0).size() == numItemsFree) {
						itemsForFree.remove(0);
					}
				}
			}
			else if (group.size() == numItems) {
				group = new ArrayList<>();
				group.add(item);
				items.add(group);
			}
		}
		else if (item.getId() == productIdFree) {
			List<ProductItem> group = itemsForFree.get(itemsForFree.size() - 1);

			if (group.size() < numItemsFree) {
				group.add(item);

				if (group.size() == numItemsFree
						&& items.get(items.size() - 1).size() == numItems) {
					item.setPrice(0L);
				}
			}
			else if (group.size() == numItemsFree) {
				group = new ArrayList<>();
				group.add(item);
				itemsForFree.add(group);
			}
		}
	}

}
