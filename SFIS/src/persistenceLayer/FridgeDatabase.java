package persistenceLayer;

import java.util.ArrayList;

import domainLayer.StoredItem;

public class FridgeDatabase {
	private ArrayList<FridgeItem> FridgeDB = new ArrayList<FridgeItem>();
	
	public void setFridge(Fridge fridge) {
		FridgeDB = fridge;
	}
	
	
	public void addItem(StoredItem item) {
		FridgeDB.add(item);
		
	}
	
	public ArrayList<FridgeItem> loadItems() {
	return FridgeDB;
		
	}
}
