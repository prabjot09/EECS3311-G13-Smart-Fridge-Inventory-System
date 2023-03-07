package domainTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domainLayer.DiscreteStockableItem;
import domainLayer.StockableItem;

class DiscreteStockableItemTest {

	private StockableItem stock;
	private static int INCREMENT = 1;
	
	@BeforeEach
	public void init() {
		stock = new DiscreteStockableItem(50);
	}
	
	@AfterEach
	public void teardown() {
		stock = null;
	}
	
	
	@Test
	public void initTest() {
		assertEquals(50, stock.getStock(), "Stock initializates wrong values");
		assertEquals(stock.getStock(), stock.getMax(), "Stock doesn't initialize current and max amounts as the same.");
	}

	@Test
	public void amountGetSetTest() {
		stock.setStock(30);
		assertEquals(30, stock.getStock(), "Stock doesn't set amounts correctly.");
	}
	
	@Test
	public void maxGetSetTest() {
		stock.setMax(80);
		assertEquals(80, stock.getMax(), "Stock doesn't set max values correctly.");
	}
	
	@Test
	public void maxAdjustTest() {
		stock.setStock(80);
		assertEquals(80, stock.getMax(), "Stock doesn't change max value implicitly.");
		

		stock.setMax(40);
		assertEquals(80, stock.getMax(), "Stock doesn't prevent max from being below current stock.");
	}
	
	@Test 
	public void invalidInputTest() {
		stock = new DiscreteStockableItem(-2);
		assertEquals(0, stock.getStock(), "Stock constructor fails for negative input.");
		
		stock = new DiscreteStockableItem(50);
		stock.setStock(-30);
		assertFalse(50 == stock.getStock(), "Stock is unchanged for negative input.");
		assertFalse(-30 == stock.getStock(), "Stock can be set to negative value.");
		assertTrue(0 == stock.getStock(), "Stock not set to 0 for negative input.");
		
		stock.setMax(-30);
		assertEquals(0, stock.getMax(), "Stock max doesn't default negative input to 0.");
	}
	
	@Test
	public void descriptionTest() {
		String initDesc = "50 units";
		assertTrue(stock.getDescription().equals(initDesc), "Description wrong upon init.");
		
		stock.setStock(40);
		String desc2 = "40 units";
		assertTrue(stock.getDescription().equals(desc2), "Description doesn't change with setter.");
		
		stock.setMax(90);
		String desc3 = "40 units";
		assertTrue(stock.getDescription().equals(desc3), "Description is unnecessarily affected by max amount.");
		
		stock.setStock(-109);
		String desc4 = "0 units";
		assertTrue(stock.getDescription().equals(desc4), "Negative input for stock setter gives improper description.");
	}
	
	@Test
	public void incrementTest() {
		stock.increment();
		assertEquals(50 + INCREMENT, stock.getStock(), "Increment doesn't change stock.");
		assertEquals(50 + INCREMENT, stock.getMax(), "Increment doesn't implicitly change max.");
		
		stock.setStock(30);
		stock.increment();
		assertEquals(50 + INCREMENT, stock.getMax(), "Increment affects max unnecessarily.");
	}
	
	@Test
	public void decrementTest() {
		stock.decrement();
		assertEquals(50 - INCREMENT, stock.getStock(), "Decrement doesn't change stock.");
		assertEquals(50, stock.getMax(), "Decrement unnecessarily change max.");
		
		stock.setStock(0);
		stock.decrement();
		assertEquals(0, stock.getStock(), "Decrement changes stock to negatives.");
		assertEquals(50, stock.getMax(), "Decrement unnecessarily changes max when stock tries to go negative");
	}
	
	@Test
	public void copyTest() {
		stock.setMax(70);
		
		StockableItem copy = stock.copy();
		assertEquals(50, copy.getStock(), "Copy doesn't set stock correctly.");
		assertEquals(70, copy.getMax(), "Copy doesn't set max correctly.");
		
		copy.increment();
		assertEquals(50, stock.getStock(), "Copy is not independent of original.");
	}
	
}
