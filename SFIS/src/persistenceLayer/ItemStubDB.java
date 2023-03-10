package persistenceLayer;

import java.util.ArrayList;

public class ItemStubDB {
	private ArrayList<String> ItemDB = new ArrayList<String>();

	public ItemStubDB() {
		//Simple items we instatiate at runtime
		ItemDB.add("Milk - 3 Bags");
		ItemDB.add("Milk - Cartons");
		ItemDB.add("White Bread - Loafs");
		ItemDB.add("Brown Bread - Loafs");
		ItemDB.add("Sweet Jam - Jar");
		ItemDB.add("Peanut Butter - Jar");
		ItemDB.add("Cheese - Slices");
		ItemDB.add("Cheese - Cubes");
		ItemDB.add("Beer - Bottle");
		ItemDB.add("Water - Bottle");
		ItemDB.add("Apple Juice - Carton");
		ItemDB.add("Orange Juice - Carton");
		ItemDB.add("Mango Juice - Bottle");
		ItemDB.add("Eggs - Single");
		ItemDB.add("Butter - Sticks");
		ItemDB.add("Apples - Single");
		ItemDB.add("Bananas - Single");
		ItemDB.add("Celery - Single");
		ItemDB.add("Tomatoes - Single");
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
