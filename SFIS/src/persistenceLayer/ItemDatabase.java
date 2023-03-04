package persistenceLayer;

import java.util.ArrayList;

public class ItemDatabase {
	private ArrayList<String> ItemDB = new ArrayList<String>();

	public ItemDatabase() {
		//Simple items we instatiate at runtime
		ItemDB.add("Milk - 3 Bags");
		ItemDB.add("Juice - Carton");
		ItemDB.add("Eggs - Single");
		ItemDB.add("Butter - Sticks");
		ItemDB.add("Apples");
		ItemDB.add("Chocolate - Bars");
	}
	
	public ArrayList<String> getDB() {
		return ItemDB;
	}
	
	public ArrayList<String> findMatchingFoods(String name) {
		
		String searchLowercase = name.toLowerCase();
		ArrayList<String> holdMatch = new ArrayList<String>();
		
		for (int x = 0; x < ItemDB.size(); x++) {
			if (ItemDB.get(x).toLowerCase().contains(searchLowercase)) {
				holdMatch.add(ItemDB.get(x));
			}
		}

		return holdMatch;
	}
}
