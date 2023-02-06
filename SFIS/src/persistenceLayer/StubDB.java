package persistenceLayer;

import java.util.ArrayList;

import domainLayer.FoodItem;

import domainLayer.StoredItem;


public class StubDB implements DB {
	//initialize DB by creating an arraylist of fridge items as well as our initial fridge.
	private static ArrayList<StoredItem> fridgeDB = new ArrayList<StoredItem>();
	
	
	//Addition of our base items to the DB
	
	
	//Simple return method for our initial Fridge
	public ArrayList<StoredItem> getDB() {
		
		return fridgeDB;
	}
	
	
	
	public String findMatchingFoods(String name) {
		
		return null;
	}

	
	public  void addItem(StoredItem item) {
		
		fridgeDB.add(item);
		
	}

	
	public void loadItems() {
		// TODO Auto-generated method stub
		
	}



	
}
