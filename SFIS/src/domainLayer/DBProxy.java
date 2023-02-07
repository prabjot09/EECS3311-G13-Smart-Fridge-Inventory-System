package domainLayer;


import persistenceLayer.StubDB;

public class DBProxy {
	StubDB DB = new StubDB();
	
	
	//public void loadFridge(Fridge fridge) {
	//	return fridge;
//	}
	
	public void addItem(StoredItem item) {
		DB.addItem(item);
	}
	
	
}
