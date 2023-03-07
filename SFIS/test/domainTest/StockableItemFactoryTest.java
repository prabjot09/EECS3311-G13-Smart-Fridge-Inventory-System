package domainTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import domainLayer.ContinuousStockableItem;
import domainLayer.DiscreteStockableItem;
import domainLayer.FoodItem;
import domainLayer.StockableItem;
import domainLayer.StockableItemFactory;

class StockableItemFactoryTest {

	@Test
	void continuousTypeTest() {
		StockableItem stock = StockableItemFactory.createStockableItem(FoodItem.StockType.CONTINUOUS, 30);
		assertFalse(stock == null, "CONTINUOUS enum parameter leads to null object creation.");
		
		try {
			ContinuousStockableItem casted = (ContinuousStockableItem) stock;
		} catch (ClassCastException e) {
			fail("Correct class is not returned from factory.");
		}
		
		assertEquals(stock.getStock(), 30, "Amount is not set correctly.");
	}

	@Test
	void discreteTypeTest() {
		StockableItem stock = StockableItemFactory.createStockableItem(FoodItem.StockType.DISCRETE, 5);
		assertFalse(stock == null, "DISCRETE enum parameter leads to null object creation.");
		
		try {
			DiscreteStockableItem casted = (DiscreteStockableItem) stock;
		} catch (ClassCastException e) {
			fail("Correct class is not returned from factory.");
		}
		
		assertEquals(stock.getStock(), 5, "Amount is not set correctly.");
	}
	
	@Test
	void unknownTypeTest() {
		StockableItem stock = StockableItemFactory.createStockableItem(null, 5);
		assertTrue(stock == null, "null type parameter leads to non-null object creation.");
	}
}
