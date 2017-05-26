package com.eCheckoutRuler.rules;

import com.eCheckoutRuler.ProductItem;

public interface Discount {

	void apply(ProductItem item);
}
