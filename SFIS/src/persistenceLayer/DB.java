package persistenceLayer;

import java.util.ArrayList;
import java.util.List;

import domainLayer.Fridge;
import domainLayer.StoredItem;

public interface DB {
	
	public ArrayList<String> findMatchingFoods(String name);
	public void addItem(StoredItem item);
	public List<? extends StoredItem> loadItems();
	public void updateFridge(Fridge fridge);
	
}

