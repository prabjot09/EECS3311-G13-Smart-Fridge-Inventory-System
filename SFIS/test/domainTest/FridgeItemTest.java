package domainTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import domainLayer.FridgeItem;
import domainLayer.StockableItem;
import domainLayer.StockableItemFactory;
import domainLayer.FoodItem;
import domainLayer.FoodItem.StockType;

public class FridgeItemTest {
	
	private FridgeItem fridgeItem;
	
	@Before
	public void setUp() {
		fridgeItem = new FridgeItem();
	}

	@Test
	public void testExecuteIncrement() {
		StockableItem stock = StockableItemFactory.createStockableItem(StockType.DISCRETE, 1);
		fridgeItem.setStockableItem(stock);
		fridgeItem.executeIncrement();
		assertEquals(2, fridgeItem.getStockableItem().getStock());
	}
	
	@Test
	public void testExecuteDecrement() {
		StockableItem stock = StockableItemFactory.createStockableItem(StockType.DISCRETE, 2);
		fridgeItem.setStockableItem(stock);
		fridgeItem.executeDecrement();
		assertEquals(1, fridgeItem.getStockableItem().getStock());
	}
	
	@Test
	public void testCopy() {
		StockableItem stock = StockableItemFactory.createStockableItem(StockType.DISCRETE, 2);
		fridgeItem.setStockableItem(stock);
		LocalDate expDate = LocalDate.now().plusDays(7);
		fridgeItem.setExpDate(expDate);
		FridgeItem copy = fridgeItem.copy();
		assertEquals(fridgeItem.getStockableItem().getStock(), copy.getStockableItem().getStock());
		assertEquals(fridgeItem.getExpDate(), copy.getExpDate());
	}
	
	@Test
	public void testIsExpiring() {
		LocalDate expDate = LocalDate.now().plusDays(6);
		fridgeItem.setExpDate(expDate);
		assertTrue(fridgeItem.isExpiring());
	}
	
	@Test
	public void descriptionTest() {
		fridgeItem.setFoodItem(new FoodItem("Caramel Donuts", StockType.CONTINUOUS));
		fridgeItem.setStockableItem(StockableItemFactory.createStockableItem(StockType.CONTINUOUS, 50));
		
		String expected = "Caramel Donuts: Half Full";
		assertTrue(fridgeItem.getDescription().equals(expected), "Basic description is not properly generated.");
		
		fridgeItem.setExpDate(null);
		assertEquals(fridgeItem.getDescription(), expected, "Null expiry date doesn't change description.");
		
		fridgeItem.setExpDate(LocalDate.now().plusDays(9));
		String expected2 = expected + ", Exp: " + LocalDate.now().plusDays(9).toString();
		assertEquals(fridgeItem.getDescription(), expected2, "Expiry date doesn't correctly change description.");
		
		fridgeItem.setExpDate(LocalDate.now().plusDays(3));
		String expected3 = expected + ", Exp: " + LocalDate.now().plusDays(3).toString() + " [EXPIRY WARNING]";
		assertTrue(fridgeItem.getDescription().equals(expected3), "Expiration warning on expiring items is not correctly generated.");
		
	}
}
