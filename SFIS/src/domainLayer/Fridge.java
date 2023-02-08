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
	public void add(FridgeItem item) {
		fridgeItems.add(item);
	}

	@Override
	public void remove(FridgeItem item) {
		fridgeItems.remove(item);
	}
}

