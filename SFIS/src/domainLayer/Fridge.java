package domainLayer;

import java.util.*;

public class Fridge implements Inventory {
	private List<FridgeItem> fridgeItems;

	public Fridge(List<FridgeItem> fridgeItems) {
		//loop over fridgeItems
		this.fridgeItems = fridgeItems; //change this
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

