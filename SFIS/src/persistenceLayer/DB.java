package persistenceLayer;

import domainLayer.StoredItem;

public interface DB {
	
	
	
	
	public String findMatchingFoods(String name);
	public void addItem(StoredItem item);
	public void loadItems();
}

