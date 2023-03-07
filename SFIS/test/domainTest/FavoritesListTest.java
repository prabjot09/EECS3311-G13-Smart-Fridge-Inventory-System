package domainTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domainLayer.FavoritesList;
import domainLayer.FoodItem;
import domainLayer.FoodItem.StockType;
import domainLayer.StockableItem;
import domainLayer.StoredItem;

class FavoritesListTest {

	class StubBase extends StoredItem {
		public void executeIncrement() { }
		public void executeDecrement() { }
		public StoredItem copy() { 
			StockableItem stock = this.getStockableItem();
			return new StubItemX(this.getFoodItem().getName(), stock.getStock(), stock.getMax()); 
		}		
	}
	
	class StubStock extends StockableItem {
		public void increment() { }
		public void decrement() { }
		public String getDescription() { return null; }
		public StockableItem copy() { return null; }
	}
	
	class StubItemX extends StubBase {
		int stock, max;
		public StubItemX(String name, int stock, int max) {
			this.setFoodItem(new FoodItem(name, StockType.DISCRETE));
			this.stock = stock; 
			this.max = max;
			this.setStockableItem(new StubStock() {
				public int getStock() { return StubItemX.this.stock; }
				public int getMax() { return StubItemX.this.max; }
				public void setStock(int a) { StubItemX.this.stock = a; }
				public void setMax(int a) { StubItemX.this.max = a; }
			});
		}
	}
	
	private FavoritesList favorites;
	
	@BeforeEach
	public void init() {
		List<StoredItem> items = new ArrayList<>();
		items.add(new StubItemX("Pizza - Slices", 5, 5));
		items.add(new StubItemX("Chocolate - Bars", 3, 3));
		items.add(new StubItemX("Carrots", 9, 9));
		
		favorites = new FavoritesList(items);
	}
	
	@Test
	void itemDifferencesTest() {
		List<StoredItem> items = new ArrayList<>();
		items.add(new StubItemX("Coke - Cans", 1, 4));
		items.add(new StubItemX("Pizza - Slices", 2, 9));
		items.add(new StubItemX("Chocolate - Bars", 0, 21));
		items.add(new StubItemX("Ice Cream - Box", 0, 50));
		
		List<StoredItem> overlap = favorites.itemDifferences(items);
		
		assertEquals(overlap.size(), 2, "Correct number of items not returned.");
		
		List<String> matchedItems = new ArrayList<>();
		for (StoredItem item: overlap) {
			if (item.getFoodItem().getName().equals("Chocolate - Bars")) {
				matchedItems.add("Chocolate - Bars");
				assertEquals(item.getStockableItem().getStock(), 0, "Item stock not set correctly by itemDifference()");
				assertEquals(item.getStockableItem().getMax(), 3, "Item max not set correctly by itemDifference()");
			}
			else if (item.getFoodItem().getName().equals("Pizza - Slices")) {
				matchedItems.add("Pizza - Slices");
				System.out.println(item.getStockableItem().getStock());
				assertEquals(item.getStockableItem().getStock(), 2, "Item stock not set correctly by itemDifference()");
				assertEquals(item.getStockableItem().getMax(), 5, "Item max not set correctly by itemDifference()");
			}
		}
		
		assertTrue(matchedItems.contains("Chocolate - Bars"), "Misses a matching item");
		assertTrue(matchedItems.contains("Pizza - Slices"), "Misses a matching item");
	}

}
