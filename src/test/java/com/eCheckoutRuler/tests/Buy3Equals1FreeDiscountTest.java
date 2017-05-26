
package com.eCheckoutRuler.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.eCheckoutRuler.ProductItem;
import com.eCheckoutRuler.Receipt;
import com.eCheckoutRuler.SupermarketCheckOut;
import com.eCheckoutRuler.rules.Buy3Equals1FreeDiscount;
import com.eCheckoutRuler.rules.Discount;

public class Buy3Equals1FreeDiscountTest {

	private static final double PRICE_DELTA = 0L;
	private static final double PRICE = 10L;
	private static final double FREE = 0L;

	private List<ProductItem> items;
	private List<Discount> rules;
	private SupermarketCheckOut checkOut;
	private Receipt receipt;

	@Before
	public void setUp() {
		items = new ArrayList<>();
		rules = new ArrayList<>();
	}

	@Test
	public void buy1Item() {
		items.add(new ProductItem(1, PRICE));
		rules.add(new Buy3Equals1FreeDiscount());
		checkOut = new SupermarketCheckOut(rules);

		receipt = checkOut.calculatePrice(items);

		assertEquals(PRICE, receipt.getItem(0).getPrice(), PRICE_DELTA);
		assertEquals(PRICE, receipt.getTotal(), PRICE_DELTA);
	}

	@Test
	public void buy2DifferentItems() {
		items.add(new ProductItem(1, PRICE));
		items.add(new ProductItem(2, PRICE));
		rules.add(new Buy3Equals1FreeDiscount());
		checkOut = new SupermarketCheckOut(rules);

		receipt = checkOut.calculatePrice(items);

		assertEquals(PRICE, receipt.getItem(0).getPrice(), PRICE_DELTA);
		assertEquals(PRICE, receipt.getItem(1).getPrice(), PRICE_DELTA);

		assertEquals(PRICE * 2, receipt.getTotal(), PRICE_DELTA);
	}

	@Test
	public void buy2Equals1DifferentItems() {
		items.add(new ProductItem(1, PRICE));
		items.add(new ProductItem(1, PRICE));
		items.add(new ProductItem(2, PRICE));
		rules.add(new Buy3Equals1FreeDiscount());
		checkOut = new SupermarketCheckOut(rules);

		receipt = checkOut.calculatePrice(items);

		assertEquals(PRICE, receipt.getItem(0).getPrice(), PRICE_DELTA);
		assertEquals(PRICE, receipt.getItem(1).getPrice(), PRICE_DELTA);
		assertEquals(PRICE, receipt.getItem(2).getPrice(), PRICE_DELTA);

		assertEquals(PRICE * 3, receipt.getTotal(), PRICE_DELTA);
	}

	@Test
	public void buy3EqualsItems() {
		items.add(new ProductItem(1, PRICE));
		items.add(new ProductItem(1, PRICE));
		items.add(new ProductItem(1, PRICE));
		rules.add(new Buy3Equals1FreeDiscount());
		checkOut = new SupermarketCheckOut(rules);

		receipt = checkOut.calculatePrice(items);

		assertEquals(PRICE, receipt.getItem(0).getPrice(), PRICE_DELTA);
		assertEquals(PRICE, receipt.getItem(1).getPrice(), PRICE_DELTA);
		assertEquals(FREE, receipt.getItem(2).getPrice(), PRICE_DELTA);

		assertEquals(PRICE * 2, receipt.getTotal(), PRICE_DELTA);
	}
}
