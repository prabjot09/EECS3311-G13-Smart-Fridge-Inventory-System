package persistenceLayer;

import java.util.ArrayList;

import domainLayer.StoredItem;

public interface DB {
	
	
	
	
	public ArrayList<String> findMatchingFoods(String name);
	public void addItem(StoredItem item);
	public void loadItems();
}

