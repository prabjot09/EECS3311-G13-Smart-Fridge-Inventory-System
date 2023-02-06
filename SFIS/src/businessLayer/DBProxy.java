package businessLayer;

import java.util.ArrayList;

import domainLayer.Fridge;
import domainLayer.FridgeItem;

public class DBProxy implements FridgeDatabase {
	private static ArrayList<FridgeItem> fridgeDB = new ArrayList<FridgeItem>();
	Fridge mainFridge = new Fridge(fridgeDB);
	public DBProxy() {
		mainFridge.addItem(new FridgeItem("Milk", 3));
		mainFridge.addItem(new FridgeItem("Eggs", 12));
		mainFridge.addItem(new FridgeItem("Cookies", 1));
		mainFridge.addItem(new FridgeItem("Water", 54));
		mainFridge.addItem(new FridgeItem("Juice", 4));
	}
	
	public Fridge getDB() {
		
		return mainFridge;
	}

	@Override
	public String findMatchingFoods(String name) {
		
		for (int x = 0; x < mainFridge.getFridgeSize(); x++) {
			if (name.toLowerCase().compareTo(mainFridge.getFridge().get(x).getItem().toLowerCase()) == 0) {
				return mainFridge.getFridge().get(x).getItem();
				
			}
		
		}
		// TODO Auto-generated method stub
		return "Could not find item";
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
