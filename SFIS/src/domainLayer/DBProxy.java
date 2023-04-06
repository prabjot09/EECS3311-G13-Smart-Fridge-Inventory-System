package domainLayer;


import java.io.File;
import java.util.List;

import persistenceLayer.StubDB;
import persistenceLayer.DB;

public class DBProxy {
	private static DBProxy DBProxy = null;
	
	private DB db;
	
	private DBProxy() {

	}
	
	//Our proxy, ensuring we only get 1 instance of our DB
	public static DBProxy getInstance() {
		if (DBProxy == null) {
			DBProxy = new DBProxy(); 
		}
		return DBProxy;
	}
	
	public void setDB(DB db) {
		this.db = db;
	}
	
	public List<StoredItem> loadItems() {
		return db.loadItems();
	}
	

	public void addItem(FridgeItem item) {
		db.addItem(item);
	}
	
	
	public void updateFridge(Fridge fridge) {
		db.updateFridge(fridge);
	}
	
	public List<String> findItemDBItems(String name){
		return db.findMatchingFoods(name);
	}

	public List<StoredItem> loadFavoritedItems() {
		return db.loadFavoritedItems();
	}
	
	public void updateFavoritedItems(FavoritesList favorites) {
		db.updateFavoritedItems(favorites);
	}
	
	public List<StoredItem> loadGroceryItems() {
		return db.loadGroceryItems();
	}
	
	public void updateGroceryItems(GroceryList groceries) {
		db.updateGroceryItems(groceries);
	}
	
	public List<Recipe> loadRecipes(){
		return db.getRecipeDB();
	}
	
	public UserHistory loadUserHistory() {
		return db.loadUserHistory();
	}
	public void updateUserHistory(UserHistory history) {
		db.updateUserHistory(history);
	}
	
	public void updateUserSettings(UserSettings settings) {
        db.updateUserSettings(settings);
    }
	public UserSettings loadUserSettings() {
        return db.loadUserSettings();
    }
	
	public void exportData(String tables, File file) {
		db.exportDB(tables, file);
	}
	
	public void importData(File file) {
		db.importDB(file);
	}
}
