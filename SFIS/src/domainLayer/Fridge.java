package domainLayer;

import java.util.*;

public class Fridge implements Inventory {
	private List<StoredItem> fridgeItems;

	public Fridge(List<StoredItem> items) {
	    this.fridgeItems = new ArrayList<StoredItem>();
	    for (StoredItem item : items) {
	        this.fridgeItems.add(item);
	    }
	}
	
	public List<StoredItem> getFridgeItems() {
		List<StoredItem> fridgeItems = new ArrayList<StoredItem>();
	    for (StoredItem item : this.fridgeItems) {
	        fridgeItems.add(item);
	    }
	    return fridgeItems;
	}

	public void setFridgeItems(List<StoredItem> fridgeItems) {
		this.fridgeItems = new ArrayList<StoredItem>();
	    for (StoredItem item : fridgeItems) {
	        this.fridgeItems.add(item);
	    }
	}

	@Override
	public List<StoredItem> search(String name) {
	    List<StoredItem> foundFrigeItems = new ArrayList<StoredItem>();
	    for (StoredItem item : fridgeItems) {
	        if (item.getFoodItem().getName().toLowerCase().contains(name.toLowerCase())) {
	            foundFrigeItems.add(new FridgeItem((FridgeItem) item));
	        }
	    }
	    return foundFrigeItems;
	}

	@Override
	public void add(StoredItem item) throws Exception {
		String inputItemName = item.getFoodItem().getName();
		for (StoredItem itemsInFridge : fridgeItems) {
			if (inputItemName.equalsIgnoreCase(itemsInFridge.getFoodItem().getName())) {
				throw new Exception("Item already exists within fridge");
			}
		}
		
		FridgeItem thisItem = (FridgeItem) item;
		fridgeItems.add(thisItem);
	}

	@Override
	public void remove(StoredItem item) {
		fridgeItems.remove((FridgeItem) item);
	}
}

