package domainTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domainLayer.AlphabeticalSorting;
import domainLayer.DepletedSorting;
import domainLayer.ISortingStrategy;
import domainLayer.ItemManager;
import domainLayer.StoredItem;

class ItemManagerTest {

	private ItemManager list;
	private List<StoredItem> itemCopies;
	private List<StoredItem> AphabeticallySortedCopy;
	private List<StoredItem> DepletedSortedCopy;
	
	@BeforeEach
	public void init() {
		StoredItem item1 = new StubItemX("Pizza - Slices", 5, 6);
		StoredItem item2 = new StubItemX("Milk - Cartons", 3, 5);
		StoredItem item3 = new StubItemX("Water - Bottles", 9, 15);
		StoredItem item4 = new StubItemX("Apple Pie - Slices", 1, 12);
		StoredItem item5 = new StubItemX("Eggs - Single", 3, 12);
		StoredItem item6 = new StubItemX("Chocolate - Bars", 0, 9);
		StoredItem item7 = new StubItemX("Cheese - Slices", 10, 25);
		StoredItem item8 = new StubItemX("Orange Juice - Cartons", 11, 14);
		
		StoredItem[] items = {item1, item2, item3, item4, item5, item6, item7, item8};
		itemCopies = Arrays.asList(items);
		
		StoredItem[] AphabeticallySorted = {item4, item7, item6, item5, item2, item8, item1, item3}; // alpahbetical order
		AphabeticallySortedCopy = Arrays.asList(AphabeticallySorted);
		
		StoredItem[] DepletedSorted = {item7, item4, item5, item6, item3, item8, item2, item1}; //depleted order
		DepletedSortedCopy = Arrays.asList(DepletedSorted);
		
		list = new ItemManager(itemCopies);
		
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
		ISortingStrategy AplhSorter = new AlphabeticalSorting();
		ISortingStrategy DepletionSorter = new DepletedSorting();
		
		List<StoredItem> sortedItems = AphabeticallySortedCopy;
		List<StoredItem> test = list.getItems(AplhSorter);
		
		for (int i = 0; i < sortedItems.size(); i++) {
		    StoredItem item1 = sortedItems.get(i);
		    StoredItem item2 = test.get(i);
		    assertEquals(item1.getFoodItem().getName(), item2.getFoodItem().getName()); // Compare name
		}
		
		sortedItems = DepletedSortedCopy;
		test = list.getItems(DepletionSorter);
		for (int i = 0; i < sortedItems.size(); i++) {
		    StoredItem item1 = sortedItems.get(i);
		    StoredItem item2 = test.get(i);
		    assertEquals(item1.getFoodItem().getName(), item2.getFoodItem().getName()); // Compare name
		}
		
	}

}
