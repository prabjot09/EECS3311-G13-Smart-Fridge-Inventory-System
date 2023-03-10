package domainTest;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import domainLayer.FridgeItem;
import domainLayer.StockableItem;
import domainLayer.StockableItemFactory;
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
}
