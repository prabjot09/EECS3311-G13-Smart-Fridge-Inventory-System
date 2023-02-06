package businessLayer;

import java.util.ArrayList;
import java.util.List;

import domainLayer.FridgeItem;

interface FridgeDatabase {
	
	
	
	
	public String findMatchingFoods(String name);
	public void addItem();
	public void loadItems();
}
