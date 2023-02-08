package domainLayer;

import java.util.*;

public class Fridge implements Inventory {
	private List<FridgeItem> fridgeItems;

	public Fridge(List<FridgeItem> fridgeItems) {
	    this.fridgeItems = new ArrayList<FridgeItem>();
	    for (FridgeItem item : fridgeItems) {
	        FridgeItem newItem = new FridgeItem();
	        newItem.setFoodItem(new FoodItem());
	        newItem.getFoodItem().setName(item.getFoodItem().getName());
	        newItem.setStockableItem(item.getStockableItem());
	        this.fridgeItems.add(newItem);
	    }
	}
	
	public List<FridgeItem> getFridgeItems() {
		List<FridgeItem> fridgeItems = new ArrayList<FridgeItem>();
	    for (FridgeItem item : this.fridgeItems) {
	        FridgeItem newItem = new FridgeItem();
	        newItem.setFoodItem(new FoodItem());
	        newItem.getFoodItem().setName(item.getFoodItem().getName());
	        newItem.setStockableItem(item.getStockableItem());
	        fridgeItems.add(newItem);
	    }
	    return fridgeItems;
	}

	public void setFridgeItems(List<FridgeItem> fridgeItems) {
		this.fridgeItems = new ArrayList<FridgeItem>();
	    for (FridgeItem item : fridgeItems) {
	        FridgeItem newItem = new FridgeItem();
	        newItem.setFoodItem(new FoodItem());
	        newItem.getFoodItem().setName(item.getFoodItem().getName());
	        newItem.setStockableItem(item.getStockableItem());
	        this.fridgeItems.add(newItem);
	    }
	}

	@Override
	public List<FridgeItem> search(String name) {
	    List<FridgeItem> foundFrigeItems = new ArrayList<FridgeItem>();
	    for (FridgeItem item : fridgeItems) {
	        if (item.getFoodItem().getName().contains(name)) {
	            foundFrigeItems.add(item);
	        }
	    }
	    return foundFrigeItems;
	}

	@Override
	public void add(StoredItem item) {
		fridgeItems.add((FridgeItem) item);
	}

	@Override
	public void remove(StoredItem item) {
		fridgeItems.remove((FridgeItem) item);
	}
}

