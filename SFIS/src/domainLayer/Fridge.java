package domainLayer;

import java.util.*;

public class Fridge implements Inventory {
	private List<StoredItem> fridgeItems = new ArrayList<StoredItem>();

	public Fridge(List<StoredItem> items) {
	    this.fridgeItems = new ArrayList<StoredItem>();
	    for (StoredItem item : items) {
	        FridgeItem newItem = new FridgeItem();
	        newItem.setFoodItem(new FoodItem());
	        newItem.getFoodItem().setName(item.getFoodItem().getName());
	        newItem.setStockableItem(item.getStockableItem());
	        this.fridgeItems.add(newItem);
	    }
	}
	
	public List<StoredItem> getFridgeItems() {
		List<StoredItem> fridgeItems = new ArrayList<StoredItem>();
	    for (StoredItem item : this.fridgeItems) {
	        FridgeItem newItem = new FridgeItem();
	        newItem.setFoodItem(new FoodItem());
	        newItem.getFoodItem().setName(item.getFoodItem().getName());
	        newItem.setStockableItem(item.getStockableItem());
	        fridgeItems.add(newItem);
	    }
	    return fridgeItems;
	}

	public void setFridgeItems(List<? extends StoredItem> fridgeItems) {
		this.fridgeItems = new ArrayList<StoredItem>();
	    for (StoredItem item : fridgeItems) {
	        FridgeItem newItem = new FridgeItem();
	        newItem.setFoodItem(new FoodItem());
	        newItem.getFoodItem().setName(item.getFoodItem().getName());
	        newItem.setStockableItem(item.getStockableItem());
	        this.fridgeItems.add(newItem);
	    }
	}

	@Override
	public List<StoredItem> search(String name) {
	    List<StoredItem> foundFrigeItems = new ArrayList<StoredItem>();
	    for (StoredItem item : fridgeItems) {
	        if (item.getFoodItem().getName().contains(name)) {
	            foundFrigeItems.add(item);
	        }
	    }
	    return foundFrigeItems;
	}

	@Override
	public void add(StoredItem item) throws Exception {
		String inputItemName = item.getFoodItem().getName();
		for (StoredItem itemsInFridge : fridgeItems) {
			if (inputItemName.equalsIgnoreCase(itemsInFridge.getFoodName().getName())) {
				throw new Exception("Item already exists within fridge");
			}
		}
		fridgeItems.add((FridgeItem) item);
	}

	@Override
	public void remove(StoredItem item) {
		fridgeItems.remove((FridgeItem) item);
	}
}

