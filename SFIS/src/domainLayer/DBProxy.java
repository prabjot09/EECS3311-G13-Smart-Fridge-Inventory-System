package domainLayer;


import java.util.List;

import persistenceLayer.StubDB;
import persistenceLayer.DB;

public class DBProxy {
	private static DBProxy DBProxy = null;
	
	private DB db;
	
	private DBProxy() {

	}
	
	//Our proxy, ensuring we only get 1 instance of our DB
	public static DBProxy getInstance() {
		if (DBProxy == null) {
			DBProxy = new DBProxy(); 
		}
		return DBProxy;
	}
	
	public void setDB(DB db) {
		this.db = db;
	}
	
	public List<StoredItem> loadItems() {
		return db.loadItems();
	}
	

	public void addItem(FridgeItem item) {
		db.addItem(item);
	}
	
	
	public void updateFridge(Fridge fridge) {
		db.updateFridge(fridge);
	}
	
	public List<String> findItemDBItems(String name){
		return db.findMatchingFoods(name);
	}
	
}
