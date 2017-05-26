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
import com.eCheckoutRuler.rules.Buy2EqualsSpecialPriceDiscount;
import com.eCheckoutRuler.rules.Buy3Equals1FreeDiscount;
import com.eCheckoutRuler.rules.Buy3ItemsSetCheapestFreeDiscount;
import com.eCheckoutRuler.rules.Discount;
import com.eCheckoutRuler.specialprice.HalfPrice;
import com.eCheckoutRuler.specialprice.SpecialPrice;

public class CombineRulesTest {

	private static final double PRICE_DELTA = 0L;
	private static final double PRICE = 10L;

	private List<ProductItem> items;
	private List<Discount> rules;
	private SupermarketCheckOut checkOut;
	private Receipt receipt;
	private Set<Integer> itemOnSet;

	@Before
	public void setUp() {
		items = new ArrayList<>();
		rules = new ArrayList<>();

		itemOnSet = new HashSet<>();
		itemOnSet.add(1);
		itemOnSet.add(2);
		itemOnSet.add(3);
	}

	@Test
	public void buySomeEqualsSomeDifferentItems() {
		items.add(new ProductItem(1, PRICE));
		items.add(new ProductItem(2, PRICE * 2));
		items.add(new ProductItem(2, PRICE * 2));
		items.add(new ProductItem(1, PRICE));

		SpecialPrice specialPrice = new HalfPrice();
		rules.add(new Buy2EqualsSpecialPriceDiscount(specialPrice));
		rules.add(new Buy3Equals1FreeDiscount());
		rules.add(new Buy3ItemsSetCheapestFreeDiscount(itemOnSet));
		checkOut = new SupermarketCheckOut(rules);

		receipt = checkOut.calculatePrice(items);

		assertEquals(PRICE, receipt.getItem(0).getPrice(), PRICE_DELTA);
		assertEquals(PRICE * 2, receipt.getItem(1).getPrice(), PRICE_DELTA);
		assertEquals(PRICE, receipt.getItem(2).getPrice(), PRICE_DELTA);
		assertEquals(PRICE / 2, receipt.getItem(3).getPrice(), PRICE_DELTA);

		assertEquals(PRICE * 4 + (PRICE / 2), receipt.getTotal(), PRICE_DELTA);
	}
}
