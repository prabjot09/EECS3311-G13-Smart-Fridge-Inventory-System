package persistenceLayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import domainLayer.FavoritesList;
import domainLayer.Fridge;
import domainLayer.FridgeItem;
import domainLayer.GroceryList;
import domainLayer.Recipe;
import domainLayer.StoredItem;
import domainLayer.UserHistory;
import domainLayer.UserSettings;


public class StubDB implements DB {
	//We instatiate our stub here to ensure we get one instance of our database when we call our proxy
	ItemStubDB ItemDB = new ItemStubDB();
	RecipeStubDB RecipeDB = new RecipeStubDB();
	FridgeStubDB FridgeDB = new FridgeStubDB();
	FavoritesStubDB favoritesDB = new FavoritesStubDB();
	GroceryStubDB groceriesDB = new GroceryStubDB();
	HistoryStubDB historyDB = new HistoryStubDB();
	SettingsStubDB settingsDB = new SettingsStubDB();
	
	
	//given a string, we use this code to return a matching food item in our db
	public ArrayList<String> findMatchingFoods(String name) {
		return ItemDB.findMatchingFoods(name);
	}

	public void addItem(StoredItem item) {
		FridgeDB.addItem((FridgeItem) item);
	}

	public List<StoredItem> loadItems() {
		return FridgeDB.loadItems();
	}

	public void updateFridge(Fridge fridge) {
		FridgeDB.setFridge(fridge);
	}

	@Override
	public List<StoredItem> loadFavoritedItems() {
		return favoritesDB.loadFavoritedItems();
	}

	@Override
	public void updateFavoritedItems(FavoritesList favorites) {
		favoritesDB.updateFavoritedItems(favorites);
	}

	@Override
	public List<StoredItem> loadGroceryItems() {
		return groceriesDB.loadGroceryItems();
	}

	@Override
	public void updateGroceryItems(GroceryList groceries) {
		groceriesDB.updateGroceryItems(groceries);
	}

	@Override
	public UserHistory loadUserHistory() {
		return new UserHistory(historyDB.getHistoryData(), historyDB.getRecalibrationDate(), historyDB.getModificationDate());
	}

	@Override
	public void updateUserHistory(UserHistory history) {
		historyDB.setHistoryData(history.getData());
		historyDB.setRecalibrationDate(history.getRecalibrationDate());
		historyDB.setModificationDate(history.getModificationDate());
	}

	@Override
	public void exportDB(String tables, File file) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void importDB(File file) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUserSettings(UserSettings settings) {
		settingsDB.updateUserSettings(settings);		
	}

	@Override
	public UserSettings loadUserSettings() {
		return settingsDB.loadUserSettings();
	}

	@Override
	public List<Recipe> getRecipeDB(){
		return RecipeDB.getRecipeDB();
	}	
}
