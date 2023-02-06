package persistenceLayer;

import java.util.ArrayList;

import domainLayer.Fridge;
import domainLayer.FridgeItem;

public class DBProxy implements FridgeDatabase {
	private static ArrayList<FridgeItem> fridgeDB = new ArrayList<FridgeItem>();
	Fridge mainFridge = new Fridge(fridgeDB);
	public DBProxy() {
		mainFridge.addItem(new FridgeItem("Milk - 3 Bags", 0));
		mainFridge.addItem(new FridgeItem("Eggs - 12 Pack", 0));
		
		mainFridge.addItem(new FridgeItem("Water - Bottles", 0));
		mainFridge.addItem(new FridgeItem("Juice - Carton", 0));
	}
	
	public Fridge getDB() {
		
		return mainFridge;
	}

	@Override
	public String findMatchingFoods(String name) {
		
		return null;
	}

	@Override
	public void addItem() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadItems() {
		// TODO Auto-generated method stub
		
	}
}
