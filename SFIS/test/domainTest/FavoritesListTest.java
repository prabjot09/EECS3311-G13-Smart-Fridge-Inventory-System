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
		
		assertEquals(overlap.size(), 3, "Correct number of items not returned.");
		
		List<String> matchedItems = new ArrayList<>();
		for (StoredItem item: overlap) {
			if (item.getFoodItem().getName().equals("Chocolate - Bars")) {
				matchedItems.add("Chocolate - Bars");
				assertEquals(item.getStockableItem().getStock(), 0, "Item stock not set correctly by itemDifference()");
				assertEquals(item.getStockableItem().getMax(), 3, "Item max not set correctly by itemDifference()");
			}
			else if (item.getFoodItem().getName().equals("Pizza - Slices")) {
				matchedItems.add("Pizza - Slices");
				assertEquals(item.getStockableItem().getStock(), 2, "Item stock not set correctly by itemDifference()");
				assertEquals(item.getStockableItem().getMax(), 5, "Item max not set correctly by itemDifference()");
			}
			else if (item.getFoodItem().getName().equals("Carrots")) {
				matchedItems.add("Carrots");
				assertEquals(item.getStockableItem().getStock(), 9, "Item stock not set correctly by itemDifference()");
				assertEquals(item.getStockableItem().getMax(), 9, "Item max not set correctly by itemDifference()");
			}
		}
		
		assertTrue(matchedItems.contains("Chocolate - Bars"), "Misses a matching item");
		assertTrue(matchedItems.contains("Pizza - Slices"), "Misses a matching item");
		assertTrue(matchedItems.contains("Carrots"), "Misses a matching item");
	}

}
