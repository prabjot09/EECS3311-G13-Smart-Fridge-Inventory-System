package domainLayer;


import java.util.List;

import persistenceLayer.StubDB;

public class DBProxy {
	private static DBProxy DB = null;
	
	public StubDB stubDB;
	
	private DBProxy() {
		stubDB = new StubDB();
	}
	
	private static DBProxy getInstance() {
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
	
}