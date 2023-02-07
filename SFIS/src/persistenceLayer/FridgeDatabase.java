package persistenceLayer;

import java.util.ArrayList;

import domainLayer.StoredItem;

public class FridgeDatabase {
	private ArrayList<StoredItem> FridgeDB = new ArrayList<StoredItem>();
	
	public void setFridge(Fridge fridge) {
		FridgeDB = fridge;
	}
	
	
	public void addItem(StoredItem item) {
		FridgeDB.add(item);
		
	}
	
	public ArrayList<StoredItem> loadItems() {
	return FridgeDB;
		
	}
}
