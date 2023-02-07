package persistenceLayer;

import java.util.ArrayList;

public class ItemDatabase {
	private ArrayList<String> ItemDB = new ArrayList<String>();

	public ItemDatabase() {
	ItemDB.add("Milk - 3 Bags");
	ItemDB.add("Juice - Carton");
	ItemDB.add("Eggs - Dozen");
	ItemDB.add("Butter - Sticks");
	}
	public ArrayList<String> getDB() {
		return ItemDB;
	}
	
	public ArrayList<String> findMatchingFoods(String name) {
		
		ArrayList<String> holdMatch = new ArrayList<String>();
		
		for (int x = 0; x < ItemDB.size(); x++) {
			if (ItemDB.get(x).contains(name)) {
				holdMatch.add(name);
			}
		}
		if (holdMatch.size() != 0) {
			return holdMatch;
		}
		else
		{
			return null;
		}
	}
}
