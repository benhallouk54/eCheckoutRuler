package com.eCheckoutRuler.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.eCheckoutRuler.ProductItem;
import com.eCheckoutRuler.Receipt;
import com.eCheckoutRuler.SupermarketCheckOut;
import com.eCheckoutRuler.rules.Buy3ItemsSetCheapestFreeDiscount;
import com.eCheckoutRuler.rules.Discount;

public class Buy3ItemsSetCheapestFreeDiscountTest {

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

		Set<Integer> itemOnSet = new HashSet<>();
		itemOnSet.add(1);
		itemOnSet.add(2);
		itemOnSet.add(3);

		rules.add(new Buy3ItemsSetCheapestFreeDiscount(itemOnSet));
		checkOut = new SupermarketCheckOut(rules);
	}

	@Test
	public void buy3ItemsOnSet() {
		items.add(new ProductItem(1, PRICE));
		items.add(new ProductItem(2, PRICE - 1));
		items.add(new ProductItem(3, PRICE - 2));

		receipt = checkOut.calculatePrice(items);

		assertEquals(PRICE, receipt.getItem(0).getPrice(), PRICE_DELTA);
		assertEquals(PRICE - 1, receipt.getItem(1).getPrice(), PRICE_DELTA);
		assertEquals(FREE, receipt.getItem(2).getPrice(), PRICE_DELTA);

		assertEquals(PRICE * 2 - 1, receipt.getTotal(), PRICE_DELTA);
	}

	@Test
	public void buy3ItemsOnSetUnorder() {
		items.add(new ProductItem(1, PRICE));
		items.add(new ProductItem(1, PRICE));
		items.add(new ProductItem(3, PRICE - 2));
		items.add(new ProductItem(1, PRICE));
		items.add(new ProductItem(2, PRICE - 1));

		receipt = checkOut.calculatePrice(items);

		assertEquals(PRICE, receipt.getItem(0).getPrice(), PRICE_DELTA);
		assertEquals(PRICE, receipt.getItem(1).getPrice(), PRICE_DELTA);
		assertEquals(FREE, receipt.getItem(2).getPrice(), PRICE_DELTA);
		assertEquals(PRICE, receipt.getItem(3).getPrice(), PRICE_DELTA);
		assertEquals(PRICE - 1, receipt.getItem(4).getPrice(), PRICE_DELTA);

		assertEquals(PRICE * 4 - 1, receipt.getTotal(), PRICE_DELTA);
	}

	@Test
	public void buyTwoSets() {
		items.add(new ProductItem(1, PRICE));
		items.add(new ProductItem(1, PRICE));
		items.add(new ProductItem(3, PRICE - 2));
		items.add(new ProductItem(1, PRICE));
		items.add(new ProductItem(2, PRICE - 1));
		items.add(new ProductItem(2, PRICE - 1));
		items.add(new ProductItem(3, PRICE - 2));

		receipt = checkOut.calculatePrice(items);

		assertEquals(PRICE, receipt.getItem(0).getPrice(), PRICE_DELTA);
		assertEquals(PRICE, receipt.getItem(1).getPrice(), PRICE_DELTA);
		assertEquals(FREE, receipt.getItem(2).getPrice(), PRICE_DELTA);
		assertEquals(PRICE, receipt.getItem(3).getPrice(), PRICE_DELTA);
		assertEquals(PRICE - 1, receipt.getItem(4).getPrice(), PRICE_DELTA);
		assertEquals(PRICE - 1, receipt.getItem(5).getPrice(), PRICE_DELTA);
		assertEquals(FREE, receipt.getItem(6).getPrice(), PRICE_DELTA);

		assertEquals(PRICE * 5 - 2, receipt.getTotal(), PRICE_DELTA);
	}
}
