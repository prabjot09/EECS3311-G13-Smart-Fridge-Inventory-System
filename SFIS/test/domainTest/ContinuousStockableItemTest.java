package domainTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domainLayer.ContinuousStockableItem;
import domainLayer.StockableItem;

class ContinuousStockableItemTest {

	private StockableItem stock;
	private static int INCREMENT = 25;
	
	@BeforeEach
	public void init() {
		stock = new ContinuousStockableItem(50);
	}
	
	@AfterEach
	public void teardown() {
		stock = null;
	}
	
	
	@Test
	public void initTest() {
		assertEquals(50, stock.getStock(), "Stock initializates wrong values.");
		assertEquals(100, stock.getMax(), "Max is not init to 100.");
	}

	@Test
	public void amountGetSetTest() {
		stock.setStock(30);
		assertEquals(30, stock.getStock(), "Stock doesn't set amounts correctly.");
	}
	
	@Test
	public void maxGetSetTest() {
		stock.setMax(80);
		assertEquals(100, stock.getMax(), "Stock doesn't always keeps max at 100.");
	}
	
	@Test
	public void maxAdjustTest() {
		stock.setStock(80);
		assertEquals(100, stock.getMax(), "Stock setter changes max.");
	}
	
	@Test 
	public void invalidInputTest() {
		stock = new ContinuousStockableItem(-2);
		assertEquals(0, stock.getStock(), "Stock constructor fails for negative input.");
		
		stock = new ContinuousStockableItem(50);
		stock.setStock(-30);
		assertFalse(50 == stock.getStock(), "Stock is unchanged for negative input.");
		assertFalse(-30 == stock.getStock(), "Stock can be set to negative value.");
		assertTrue(0 == stock.getStock(), "Stock not set to 0 for negative input.");
		
		stock.setMax(-30);
		assertEquals(100, stock.getMax(), "Stock doesn't stay at 100 for negative input.");
		
		stock.setStock(150);
		assertEquals(100, stock.getStock(), "Stock is not correctly upper-bounded at 100");
	}
	
	@Test
	public void descriptionTest() {
		String initDesc = "Half Full";
		assertTrue(stock.getDescription().equals(initDesc), "Description wrong upon init.");
		
		stock.setStock(25);
		String desc2 = "Almost Empty";
		assertTrue(stock.getDescription().equals(desc2), "Description doesn't change with setter.");
		
		stock.decrement();
		String desc2point5 = "Empty";
		assertTrue(stock.getDescription().equals(desc2point5), "Description is not updated by indirect stock change.");
		
		stock.setStock(75);
		String desc2point9 = "Mostly Full";
		assertTrue(stock.getDescription().equals(desc2point9), "Description doesn't match at 75% capacity.");
		
		stock.setStock(100);
		String desc2point99 = "Full";
		assertTrue(stock.getDescription().equals(desc2point99), "Description doesn't match at 100% capacity.");
		
		stock.setMax(90);
		String desc3 = "Full";
		assertTrue(stock.getDescription().equals(desc3), "Description is unnecessarily affected by max amount.");
		
		stock.setStock(-109);
		String desc4 = "Empty";
		assertTrue(stock.getDescription().equals(desc4), "Negative input for stock setter gives improper description.");
	}
	
	@Test
	public void incrementTest() {
		stock.increment();
		assertEquals(50 + INCREMENT, stock.getStock(), "Increment doesn't change stock.");
		assertEquals(100, stock.getMax(), "Increment changes max from 100.");
		
		stock.setStock(30);
		stock.increment();
		assertEquals(100, stock.getMax(), "Increment affects max unnecessarily.");
	}
	
	@Test
	public void decrementTest() {
		stock.decrement();
		assertEquals(50 - INCREMENT, stock.getStock(), "Decrement doesn't change stock.");
		assertEquals(100, stock.getMax(), "Decrement unnecessarily changes max.");
		
		stock.setStock(0);
		stock.decrement();
		assertEquals(0, stock.getStock(), "Decrement changes stock to negatives.");
		assertEquals(100, stock.getMax(), "Decrement unnecessarily changes max when stock tries to go negative");
	}
	
	@Test
	public void copyTest() {
		StockableItem copy = stock.copy();
		assertEquals(50, copy.getStock(), "Copy doesn't set stock correctly.");
		assertEquals(100, copy.getMax(), "Copy doesn't set max correctly.");
		
		copy.increment();
		assertEquals(50, stock.getStock(), "Copy is not independent of original.");
	}
	
	
	@Test
	public void percentTest() {
		assertEquals(50, stock.calculatePercent(), "Percent is not generated correctly on new object");
		
		stock.increment();
		stock.increment();
		
		assertEquals(100, stock.calculatePercent(), "Percent is not generated correctly after stock has changed.");
		
		stock.setMax(90);
		
		assertEquals(100, stock.calculatePercent(), "Percentage is changed by change in max stock which should always stay at 100%.");
		
		stock.setStock(9);
		assertEquals(9, stock.calculatePercent(), "Percentage doesn't reflect change in current stock.");
		
	}
}
