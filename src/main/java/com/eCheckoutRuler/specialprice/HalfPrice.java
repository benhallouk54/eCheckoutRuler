package com.eCheckoutRuler.specialprice;

import com.eCheckoutRuler.ProductItem;

public class HalfPrice implements SpecialPrice {

	@Override
	public void applySpecialPrice(ProductItem item) {
		item.setPrice(item.getPrice() / 2);
	}

}
