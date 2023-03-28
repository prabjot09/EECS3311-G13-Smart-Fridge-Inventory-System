package domainTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import domainLayer.Fridge;
import domainLayer.FridgeItem;
import domainLayer.ItemManager;
import domainLayer.StoredItem;
import domainLayer.DiscreteStockableItem;
import domainLayer.FoodItem;
import domainLayer.FoodItem.StockType;
import domainLayer.itemSorting.AlphabeticalSorting;

public class FridgeTest {
	
	private Fridge list;
	private List<StoredItem> itemCopies;
	
	private class ExpirableStubItem extends FridgeItem {
		private ExpirableStubItem(String name, LocalDate date) {
			this.setFoodItem(new FoodItem(name, StockType.DISCRETE));
			this.setStockableItem(new DiscreteStockableItem());
			this.setExpDate(date);
		}
	}
	
	
	@Before
	public void init() {
		StoredItem item1 = new ExpirableStubItem("Pizza - Slices", LocalDate.now().plusDays(3));
		StoredItem item2 = new ExpirableStubItem("Milk - Cartons", null);
		StoredItem item3 = new ExpirableStubItem("Water - Bottles", null);
		StoredItem item4 = new ExpirableStubItem("Apple Pie - Slices", LocalDate.now().plusDays(7));
		StoredItem item5 = new ExpirableStubItem("Eggs - Single", null);
		StoredItem item6 = new ExpirableStubItem("Chocolate - Bars", LocalDate.now().minusDays(6));
		StoredItem item7 = new ExpirableStubItem("Cheese - Slices", null);
		StoredItem item8 = new ExpirableStubItem("Orange Juice - Cartons", null);
		
		StoredItem[] items = {item1, item2, item3, item4, item5, item6, item7, item8};
		itemCopies = Arrays.asList(items);
		
		list = new Fridge(itemCopies);
		
	}
	
	@Test
	public void expiringItemsGetterTest() {
		List<StoredItem> expiring = list.getExpiringItems();
		assertEquals(2, expiring.size(), "Incorrect number of expiring items returned.");
		
		boolean result = expiring.get(0).sameItemDescription(itemCopies.get(0)) || expiring.get(0).sameItemDescription(itemCopies.get(5));
		assertTrue(result, "Missing item");
		

		boolean result2 = expiring.get(1).sameItemDescription(itemCopies.get(0)) || expiring.get(1).sameItemDescription(itemCopies.get(5));
		assertTrue(result2, "Missing item");
		
		assertFalse(expiring.get(0).sameItemDescription(expiring.get(1)), "Same 2 items returned for expiring items list.");
	}
	
	@Test
	public void expiringPrioritizationTest() {
		try {
			list.add(new ExpirableStubItem("Peanut Butter", LocalDate.now().plusDays(1)));
		} catch (Exception e) {
			fail("Unexpected failed addition due to: " + e.getMessage());
		}
		
		List<StoredItem> expiring = list.getExpiringItems();
		list.setSortingStrategy(new AlphabeticalSorting());
		list.sort();
		List<StoredItem> topList =  list.getItems().subList(0, 3);
		assertEquals(expiring.size(), 3, "Expiry of new item addition not accounted for.");
		
		assertTrue(expiring.get(0).sameItemDescription(topList.get(0)) ||
				   expiring.get(0).sameItemDescription(topList.get(1)) ||
				   expiring.get(0).sameItemDescription(topList.get(2)), 
				   "Expired items not prioritized. Error in item: " + expiring.get(0).getDescription());
		
		assertTrue(expiring.get(1).sameItemDescription(topList.get(0)) ||
				   expiring.get(1).sameItemDescription(topList.get(1)) ||
				   expiring.get(1).sameItemDescription(topList.get(2)), 
				   "Expired items not prioritized. Error in item: " + expiring.get(0).getDescription());
		
		assertTrue(expiring.get(2).sameItemDescription(topList.get(0)) ||
				   expiring.get(2).sameItemDescription(topList.get(1)) ||
				   expiring.get(2).sameItemDescription(topList.get(2)), 
				   "Expired items not prioritized. Error in item: " + expiring.get(0).getDescription());

		
	}
	
	
	

}
