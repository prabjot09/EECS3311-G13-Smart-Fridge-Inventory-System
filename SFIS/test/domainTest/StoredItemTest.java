package domainTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import domainLayer.FoodItem;
import domainLayer.FoodItem.StockType;
import domainLayer.StockableItem;
import domainLayer.StoredItem;

class StoredItemTest {
	
	class DummyItem extends StoredItem {
		public void executeIncrement() { }
		public void executeDecrement() { }
		public StoredItem copy() { return null; }
		
	}
	
	class StubFood1 extends FoodItem {
		public String getName() {
			return "Chocolate Milk - Cartons";
		}
	}
	
	class StubFood2 extends FoodItem {
		public String getName() {
			return "Pizza - Slices";
		}
	}
	
	class StubStock1 extends StockableItem {
		public void increment() { }
		public void decrement() { }
		public String getDescription() { 
			return "50%";
		}
		public StockableItem copy() { return null; }
		public StockableItem refillQuantity() { return null; }
		public boolean stockWithinBounds() { return true; }
	}
	
	@Test
	public void descriptionTest() {
		StoredItem item = new DummyItem();
		item.setFoodItem(new StubFood1());
		item.setStockableItem(new StubStock1());
		
		String expected = "Chocolate Milk - Cartons: 50%";
		assertTrue(item.getDescription().equals(expected), "Description formatting is incorrect.");
	}
	
	@Test
	public void sameDescriptionTest() {
		FoodItem food1 = new StubFood1();
		FoodItem food2 = new StubFood1();
		
		StoredItem item1 = new DummyItem();
		item1.setFoodItem(food1);
		StoredItem item2 = new DummyItem();
		item2.setFoodItem(food2);
		
		assertEquals(true, item1.sameItemDescription(item2), "sameAs is not called correctly within sameItemDescription()");
		
		FoodItem food3 = new StubFood2();
		item2.setFoodItem(food3);
		
		assertEquals(false, item1.sameItemDescription(item2), "sameAs is not called correctly within sameItemDescription()");
	}
	

}
