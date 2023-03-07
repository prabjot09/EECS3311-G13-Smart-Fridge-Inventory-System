package persistenceLayer;

import java.util.ArrayList;
import java.util.List;

import domainLayer.FavoritesList;
import domainLayer.FoodItem;
import domainLayer.Fridge;
import domainLayer.FridgeItem;
import domainLayer.StoredItem;


public class StubDB implements DB {
	//We instatiate our stub here to ensure we get one instance of our database when we call our proxy
	ItemDatabase ItemDB = new ItemDatabase();
	FridgeDatabase FridgeDB = new FridgeDatabase();

	
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
		return new ArrayList<StoredItem>();
	}

	@Override
	public void updateFavoritedItems(FavoritesList favorites) {
		// TODO Auto-generated method stub
		
	}




	
}
