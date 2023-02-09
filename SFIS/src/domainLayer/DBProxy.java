package domainLayer;


import java.util.List;

import persistenceLayer.StubDB;

public class DBProxy {
	private static DBProxy DB = null;
	
	private StubDB stubDB;
	
	private DBProxy() {
		stubDB = new StubDB();
	}
	
	public static DBProxy getInstance() {
		if (DB == null) {
			DB = new DBProxy(); 
			}
		
			return DB;
		}
	
	
	
	public List<StoredItem> loadItems() 
		{
		
		return stubDB.loadItems();
		
		}
	

	public void addItem(FridgeItem item) {
		stubDB.addItem(item);
	}
	
	
	public void updateFridge(Fridge fridge) {
		stubDB.updateFridge(fridge);
	}
	
	public List<String> findItemDBItems(String name){
		return stubDB.findMatchingFoods(name);
	}
	
}
