package com.eCheckoutRuler.tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.eCheckoutRuler.ProductItem;
import com.eCheckoutRuler.Receipt;
import com.eCheckoutRuler.SupermarketCheckOut;
import com.eCheckoutRuler.rules.Buy2EqualsSpecialPriceDiscount;
import com.eCheckoutRuler.rules.Discount;
import com.eCheckoutRuler.specialprice.HalfPrice;
import com.eCheckoutRuler.specialprice.SpecialPrice;

public class Buy2EqualsSpecialPriceDiscountTest {

	private static final double PRICE_DELTA = 0L;
	private static final double PRICE = 10L;

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
		SpecialPrice specialPrice = mock(SpecialPrice.class);
		rules.add(new Buy2EqualsSpecialPriceDiscount(specialPrice));
		checkOut = new SupermarketCheckOut(rules);

		receipt = checkOut.calculatePrice(items);

		assertEquals(PRICE, receipt.getItem(0).getPrice(), PRICE_DELTA);
		assertEquals(PRICE, receipt.getTotal(), PRICE_DELTA);
	}

	@Test
	public void buy2DifferentItems() {
		items.add(new ProductItem(1, PRICE));
		items.add(new ProductItem(2, PRICE));
		SpecialPrice specialPrice = mock(SpecialPrice.class);
		rules.add(new Buy2EqualsSpecialPriceDiscount(specialPrice));
		checkOut = new SupermarketCheckOut(rules);

		receipt = checkOut.calculatePrice(items);

		assertEquals(PRICE, receipt.getItem(0).getPrice(), PRICE_DELTA);
		assertEquals(PRICE, receipt.getItem(1).getPrice(), PRICE_DELTA);

		assertEquals(PRICE * 2, receipt.getTotal(), PRICE_DELTA);
	}

	@Test
	public void buy2EqualsItems() {
		items.add(new ProductItem(1, PRICE));
		items.add(new ProductItem(1, PRICE));
		SpecialPrice specialPrice = new HalfPrice();
		rules.add(new Buy2EqualsSpecialPriceDiscount(specialPrice));
		checkOut = new SupermarketCheckOut(rules);

		receipt = checkOut.calculatePrice(items);

		assertEquals(PRICE, receipt.getItem(0).getPrice(), PRICE_DELTA);
		assertEquals(PRICE / 2, receipt.getItem(1).getPrice(), PRICE_DELTA);

		assertEquals(PRICE + (PRICE / 2), receipt.getTotal(), PRICE_DELTA);
	}

	@Test
	public void buy3EqualsItems() {
		items.add(new ProductItem(1, PRICE));
		items.add(new ProductItem(1, PRICE));
		items.add(new ProductItem(1, PRICE));
		SpecialPrice specialPrice = new HalfPrice();
		rules.add(new Buy2EqualsSpecialPriceDiscount(specialPrice));
		checkOut = new SupermarketCheckOut(rules);

		receipt = checkOut.calculatePrice(items);

		assertEquals(PRICE, receipt.getItem(0).getPrice(), PRICE_DELTA);
		assertEquals(PRICE / 2, receipt.getItem(1).getPrice(), PRICE_DELTA);
		assertEquals(PRICE, receipt.getItem(2).getPrice(), PRICE_DELTA);

		assertEquals(PRICE * 2 + (PRICE / 2), receipt.getTotal(), PRICE_DELTA);
	}

	@Test
	public void buy4EqualsItems() {
		items.add(new ProductItem(1, PRICE));
		items.add(new ProductItem(1, PRICE));
		items.add(new ProductItem(1, PRICE));
		items.add(new ProductItem(1, PRICE));
		SpecialPrice specialPrice = new HalfPrice();
		rules.add(new Buy2EqualsSpecialPriceDiscount(specialPrice));
		checkOut = new SupermarketCheckOut(rules);

		receipt = checkOut.calculatePrice(items);

		assertEquals(PRICE, receipt.getItem(0).getPrice(), PRICE_DELTA);
		assertEquals(PRICE / 2, receipt.getItem(1).getPrice(), PRICE_DELTA);
		assertEquals(PRICE, receipt.getItem(2).getPrice(), PRICE_DELTA);
		assertEquals(PRICE / 2, receipt.getItem(3).getPrice(), PRICE_DELTA);

		assertEquals(PRICE * 3, receipt.getTotal(), PRICE_DELTA);
	}

	@Test
	public void buySomeEqualsSomeDifferentItems() {
		items.add(new ProductItem(1, PRICE));
		items.add(new ProductItem(2, PRICE * 2));
		items.add(new ProductItem(2, PRICE * 2));
		items.add(new ProductItem(1, PRICE));
		SpecialPrice specialPrice = new HalfPrice();
		rules.add(new Buy2EqualsSpecialPriceDiscount(specialPrice));
		checkOut = new SupermarketCheckOut(rules);

		receipt = checkOut.calculatePrice(items);

		assertEquals(PRICE, receipt.getItem(0).getPrice(), PRICE_DELTA);
		assertEquals(PRICE * 2, receipt.getItem(1).getPrice(), PRICE_DELTA);
		assertEquals(PRICE, receipt.getItem(2).getPrice(), PRICE_DELTA);
		assertEquals(PRICE / 2, receipt.getItem(3).getPrice(), PRICE_DELTA);

		assertEquals(PRICE * 4 + (PRICE / 2), receipt.getTotal(), PRICE_DELTA);
	}
}
