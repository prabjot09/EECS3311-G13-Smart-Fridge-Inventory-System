package persistenceLayer;

import java.util.ArrayList;
import java.util.List;

import domainLayer.Fridge;
import domainLayer.FridgeItem;

public class FridgeDatabase {
	private List<FridgeItem> FridgeDB = new ArrayList<FridgeItem>();
	
	public void setFridge(Fridge fridge) {
		FridgeDB = fridge.getFridgeItems();
	}
	
	
	public void addItem(FridgeItem item) {
		FridgeDB.add(item);
	}
	
	public List<FridgeItem> loadItems() {
		return FridgeDB;
	}
}
