package domainLayer;

import java.util.*;

public class ItemManager implements Inventory {
	private List<StoredItem> items;

	public ItemManager() {
		
	}
	
	public ItemManager(List<StoredItem> items) {
	    this.items = new ArrayList<StoredItem>();
	    for (StoredItem item : items) {
	        this.items.add(item.copy());
	    }
	}
	
	public List<StoredItem> getItems() {
		List<StoredItem> items = new ArrayList<StoredItem>();
	    for (StoredItem item : this.items) {
	        items.add(item.copy());
	    }
	    return items;
	}
	
	public List<StoredItem> getItems(ISortingStrategy sortingStrategy){
		List<StoredItem> sortedItems = sortingStrategy.sortItems(new ArrayList<>(this.items));
		return sortedItems;
	}

	public void setItems(List<StoredItem> items) {
		this.items = new ArrayList<StoredItem>();
	    for (StoredItem item : items) {
	        this.items.add(item.copy());
	    }
	}

	@Override
	public List<StoredItem> search(String name) {
		FoodItem description = new FoodItem();
		description.setName(name);
		
	    List<StoredItem> foundItems = new ArrayList<StoredItem>();
	    for (StoredItem item : items) {
	        if (item.getFoodItem().matches(description)) {
	            foundItems.add(item.copy());
	        }
	    }
	    return foundItems;
	}

	@Override
	public void add(StoredItem item) throws Exception {
		
		for (StoredItem itemsInFridge : items) {
			if (itemsInFridge.sameItemDescription(item)) {
				throw new Exception("Item already exists within fridge");
			}
		}
		
		items.add(item.copy());
	}

	@Override
	public void remove(StoredItem item) {
		int index = itemIndex(item);
		if (index == -1)
			return;
		
		this.items.remove(index);
	}
	
	public void updateItem(StoredItem item) {
		int index = itemIndex(item);
		if (index == -1)
			return;
		
		this.items.set(index, item.copy());
	}
	
	public int itemIndex(StoredItem item) {
		int index = -1;
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).sameItemDescription(item)) {
				index = i;
				break;
			}
		}
		return index;
	}
}

