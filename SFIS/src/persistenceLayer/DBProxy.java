package persistenceLayer;

import java.util.ArrayList;

import domainLayer.FoodItem;
import domainLayer.Fridge;


public class DBProxy implements FridgeDatabase {
	//initialize DB by creating an arraylist of fridge items as well as our initial fridge.
	private static ArrayList<FoodItem> fridgeDB = new ArrayList<FoodItem>();
	Fridge mainFridge = new Fridge(fridgeDB);
	
	//Addition of our base items to the DB
	public DBProxy() {
		mainFridge.addItem(new FoodItem("Milk - 3 Bags"));
		mainFridge.addItem(new FoodItem("Eggs - 12 Pack"));
		mainFridge.addItem(new FoodItem("Water - Bottles"));
		mainFridge.addItem(new FoodItem("Juice - Carton"));
	}
	
	//Simple return method for our initial Fridge
	public Fridge getDB() {
		
		return mainFridge;
	}

	
	public String findMatchingFoods(String name) {
		
		return null;
	}

	
	public void addItem() {
		// TODO Auto-generated method stub
		
	}

	
	public void loadItems() {
		// TODO Auto-generated method stub
		
	}
}
