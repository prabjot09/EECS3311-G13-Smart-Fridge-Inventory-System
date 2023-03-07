package persistenceLayer;

import java.util.ArrayList;
import java.util.List;

import domainLayer.FavoritesList;
import domainLayer.Fridge;
import domainLayer.StoredItem;

public interface DB {
	//Simple interface that contains our common DB methods
	public List<String> findMatchingFoods(String name);
	public void addItem(StoredItem item);
	public List<StoredItem> loadItems();
	public void updateFridge(Fridge fridge);
	public List<StoredItem> loadFavoritedItems();
	public void updateFavoritedItems(FavoritesList favorites);
	
}

