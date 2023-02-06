package domainLayer;

import java.util.ArrayList;

import persistenceLayer.DB;
import persistenceLayer.StubDB;

public class DBProxy implements DB {

	private StubDB proxDB = new StubDB();
	private ArrayList<StoredItem> DB = proxDB.getDB();
	private ArrayList<FoodItem> matchList = new ArrayList<FoodItem>();
	
	public ArrayList<FoodItem> findFoods(String description) {
		for (int x = 0; x < DB.size(); x++) {
			if (DB.get(x).getFoodItem().getName().contains(description)){
				matchList.add(DB.get(x).getFoodItem());
			}
		}
		
		return matchList;
	}

	@Override
	public String findMatchingFoods(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addItem(StoredItem item) {
		
		DB.add(item);
		proxDB.addItem(item);
		
	}

	@Override
	public void loadItems() {
		// TODO Auto-generated method stub
		
	}
}
