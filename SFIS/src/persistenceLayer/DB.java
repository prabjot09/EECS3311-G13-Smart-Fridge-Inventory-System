package persistenceLayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import domainLayer.FavoritesList;
import domainLayer.Fridge;
import domainLayer.GroceryList;
import domainLayer.StoredItem;
import domainLayer.UserHistory;
import domainLayer.UserSettings;

public interface DB {
	//Simple interface that contains our common DB methods
	public List<String> findMatchingFoods(String name);
	public void addItem(StoredItem item);
	public List<StoredItem> loadItems();
	public void updateFridge(Fridge fridge);
	public List<StoredItem> loadFavoritedItems();
	public void updateFavoritedItems(FavoritesList favorites);
	public List<StoredItem> loadGroceryItems();
	public void updateGroceryItems(GroceryList groceries);
	public UserHistory loadUserHistory();
	public void updateUserHistory(UserHistory history);
	public void exportDB(String tables, File file);
	public void importDB(File file);
	public void updateUserSettings(UserSettings settings);
	public UserSettings loadUserSettings();
	
}

