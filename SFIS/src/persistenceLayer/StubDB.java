package persistenceLayer;

import java.util.ArrayList;
import java.util.List;

import domainLayer.FoodItem;
import domainLayer.Fridge;
import domainLayer.StoredItem;


public class StubDB implements DB {
	
	ItemDatabase ItemDB = new ItemDatabase();
	FridgeDatabase FridgeDB = new FridgeDatabase();

	
	
	public ArrayList<String> findMatchingFoods(String name) {
		return ItemDB.findMatchingFoods(name);
	}

	public void addItem(StoredItem item) {
		FridgeDB.addItem(item);
		ItemDB.getDB().add(item.getFoodItem().getName());
	}

	public List<StoredItem> loadItems() {
		return FridgeDB.loadItems();
	}

	public void updateFridge(Fridge fridge) {
		FridgeDB.setFridge(fridge);
	}




	
}
