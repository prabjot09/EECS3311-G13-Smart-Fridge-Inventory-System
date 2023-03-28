package domainTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domainLayer.FoodItem;
import domainLayer.ItemManager;
import domainLayer.StoredItem;
import domainLayer.itemSorting.AlphabeticalSorting;
import domainLayer.itemSorting.DepletedSorting;
import domainLayer.itemSorting.ISortingStrategy;

class ItemManagerTest {

	private ItemManager list;
	private List<StoredItem> itemCopies;
	private List<StoredItem> AphabeticallySortedCopy;
	private List<StoredItem> DepletedSortedCopy;
	
	@BeforeEach
	public void init() {
		StoredItem item1 = new StubItemX("Pizza - Slices", 5, 6);
		StoredItem item2 = new StubItemX("Milk - Cartons", 3, 5);
		StoredItem item3 = new StubItemX("Water - Bottles", 10, 15);
		StoredItem item4 = new StubItemX("Apple Pie - Slices", 1, 12);
		StoredItem item5 = new StubItemX("Eggs - Single", 3, 12);
		StoredItem item6 = new StubItemX("Chocolate - Bars", 0, 9);
		StoredItem item7 = new StubItemX("Cheese - Slices", 10, 25);
		StoredItem item8 = new StubItemX("Orange Juice - Cartons", 11, 14);
		
		StoredItem[] items = {item1, item2, item3, item4, item5, item6, item7, item8};
		itemCopies = Arrays.asList(items);
		
		StoredItem[] AphabeticallySorted = {item4, item7, item6, item5, item2, item8, item1, item3}; // alpahbetical order
		AphabeticallySortedCopy = Arrays.asList(AphabeticallySorted);
		
		StoredItem[] DepletedSorted = {item6, item4, item5, item7, item2, item3, item8, item1}; //depleted order
		DepletedSortedCopy = Arrays.asList(DepletedSorted);
		
		list = new ItemManager(itemCopies);
		
	}
	
	
	@Test
	void getSetTest() {
		List<StoredItem> copy = list.getItems();
		for (int i = 0; i < copy.size(); i++) {
			assertTrue(itemCopies.get(i).sameItemDescription(copy.get(i)), "Getter doesn't get the correct copy of item " + i);
			copy.get(i).executeIncrement();
			copy.get(i).setFoodItem(new FoodItem());
			copy.get(i).getStockableItem().setStock(15);
			assertTrue(itemCopies.get(i).sameItemDescription(list.getItems().get(i)), "Composition fails to protect item " + i + " from external changes");
		}
		
		list.setItems(DepletedSortedCopy);
		copy = list.getItems();
		for (int i = 0; i < copy.size(); i++) {
			assertTrue(DepletedSortedCopy.get(i).sameItemDescription(copy.get(i)), "Setter doesn't set the correct copy of item " + i);
			DepletedSortedCopy.get(i).executeIncrement();
			DepletedSortedCopy.get(i).setFoodItem(new FoodItem());
			DepletedSortedCopy.get(i).getStockableItem().setStock(15);
			assertTrue(copy.get(i).sameItemDescription(list.getItems().get(i)), "Composition fails to protect item " + i + " from external changes");
		}
	}
	
	
	@Test
	void searchTest() {
		List<StoredItem> items =  list.search("");
		for (int i = 0; i < itemCopies.size(); i++) {
			assertTrue(itemCopies.get(i).sameItemDescription(items.get(i)), "Search doesn't return all items upon empty string input");
		}
		
		items = list.search(" - ");
		for (int i = 0; i < itemCopies.size(); i++) {
			assertTrue(itemCopies.get(i).sameItemDescription(items.get(i)), "Search doesn't return all items upon common substring (amongst all items) input");
		} 
		
		items = list.search("Slices");
		assertEquals(3, items.size(), "Search fails for input 'Slices' (whole word matching)");
		
		items = list.search("TE");
		assertEquals(2, items.size(), "Search fails for case-insensitive random string");
		
		items = list.search("Eggos");
		assertEquals(0, items.size(), "Search finds matching items to random string with no expected matches");
	}
	
	
	@Test
	void addTest() {
		try {
			list.add(new StubItemX("Pizza", 2, 3));
		} catch (Exception e) {
			fail("List doesn't add items with similar yet different names");
		}
		
		try {
			list.add(new StubItemX("Pizza - Slices", 3, 5));
			fail("List permits the addtion of items with the same name");
		} catch (Exception e) {
			
		}
		
		try {
			StoredItem itemX = new StubItemX("Maccaroni", 8, 10);
			list.add(itemX);
			assertTrue(list.getItems().size() == itemCopies.size() + 2, "Item not added properly");
			assertTrue(list.getItems().get(list.getItems().size() - 1).sameItemDescription(itemX), "Item not appended to the end of list.");
		} catch (Exception e) {
			
		}		
	}
	
	
	@Test
	void removeTest() {
		list.remove(new StubItemX("Cookies", 2, 3));
		assertEquals(list.getItems().size(), itemCopies.size(), "A non-matching item was deleted.");
		
		list.remove(new StubItemX("Cheese", 3, 5));
		assertEquals(list.getItems().size(), itemCopies.size(), "A non-matching item with similar but different name was deleted.");		

		StoredItem removed = new StubItemX("Cheese - Slices", 9, 10);
		list.remove(removed);
		assertEquals(list.getItems().size(), itemCopies.size() - 1, "A matching item with different stock was not deleted.");
		for (StoredItem item: list.getItems()) {
			assertFalse(removed.sameItemDescription(item), "The correct item was not deleted, but a different one was.");
		}
	}
	
	
	
	@Test
	void itemIndexTest() {
		List<StoredItem> a = itemCopies;
		StoredItem[] items = {a.get(2), a.get(7), a.get(5), a.get(1), a.get(6), a.get(0), a.get(4), a.get(3)};

		itemInCorrectIndex(items[0], 2);
		itemInCorrectIndex(items[1], 7);
		itemInCorrectIndex(items[2], 5);
		itemInCorrectIndex(items[3], 1);
		itemInCorrectIndex(items[4], 6);
		itemInCorrectIndex(items[5], 0);
		itemInCorrectIndex(items[6], 4);
		itemInCorrectIndex(items[7], 3);
		
	}
	
	private void itemInCorrectIndex(StoredItem storedItem, int i) {
		assertEquals(list.itemIndex(storedItem), i, "Item doesn't identify correct index for " + storedItem.getDescription() + ".");		
	}

	@Test
	void updateItemTest() {
		StoredItem pizza = itemCopies.get(0);
		pizza.getStockableItem().setStock(1);
		pizza.getStockableItem().setMax(8);
		
		list.updateItem(pizza);
		StoredItem item = list.getItems().get(0);
		assertEquals(item.getStockableItem().getStock(), 1, "Updating an item doesn't update its quantity.");
		assertEquals(item.getStockableItem().getMax(), 8, "Updating an item doesn't update its maximum quantity.");
		
		pizza.getStockableItem().setStock(7);
		pizza.getStockableItem().setMax(21);
		
		assertEquals(item.getStockableItem().getStock(), 1, "Composition doesn't protect quantity from being externally updated.");
		assertEquals(item.getStockableItem().getMax(), 8, "Composition doesn't protect maximum quantity from being externally updated.");
	}
	@Test
	void sortingTests() {
		ISortingStrategy AlphSorter = new AlphabeticalSorting();
		ISortingStrategy DepletionSorter = new DepletedSorting();
		
		List<StoredItem> sortedItems = AphabeticallySortedCopy;
		list.setSortingStrategy(AlphSorter);
		list.sort();
		List<StoredItem> test = list.getItems();
		
		for (int i = 0; i < sortedItems.size(); i++) {
		    StoredItem item1 = sortedItems.get(i);
		    StoredItem item2 = test.get(i);
		    assertEquals(item1.getFoodItem().getName(), item2.getFoodItem().getName()); // Compare name
		}
		
		sortedItems = DepletedSortedCopy;
		list.setSortingStrategy(DepletionSorter);
		list.sort();
		test = list.getItems();
		for (int i = 0; i < sortedItems.size(); i++) {
		    StoredItem item1 = sortedItems.get(i);
		    StoredItem item2 = test.get(i);
		    assertEquals(item1.getFoodItem().getName(), item2.getFoodItem().getName()); // Compare name
		}
		
	}

}
