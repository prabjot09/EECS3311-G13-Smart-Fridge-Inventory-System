package persistenceLayer;

import java.util.ArrayList;
import java.util.List;

import domainLayer.FoodItem;
import domainLayer.FridgeItem;
import domainLayer.GroceryList;
import domainLayer.StockableItemFactory;
import domainLayer.StoredItem;
import domainLayer.FoodItem.StockType;

public class GroceryStubDB {
	private List<StoredItem> groceriesDB = new ArrayList<StoredItem>();
	
	public GroceryStubDB() {
		
		FoodItem foodItem1 = new FoodItem();
		foodItem1.setName("Milk - 3 Bags");
		foodItem1.setStockType(StockType.values()[0]);
		FridgeItem fridgeItem1 = new FridgeItem();
		fridgeItem1.setStockableItem(StockableItemFactory.createStockableItem(foodItem1.getStockType(), 9));
		fridgeItem1.setFoodItem(foodItem1);
		groceriesDB.add(fridgeItem1);
		
		FoodItem foodItem2 = new FoodItem();
		foodItem2.setName("Juice - Carton");
		foodItem2.setStockType(StockType.values()[1]);
		FridgeItem fridgeItem2 = new FridgeItem();
		fridgeItem2.setStockableItem(StockableItemFactory.createStockableItem(foodItem2.getStockType(), 2));
		fridgeItem2.setFoodItem(foodItem2);
		groceriesDB.add(fridgeItem2);
		
	}
	public List<StoredItem> loadGroceryItems() {
		return groceriesDB;
	}
	
	public void updateGroceryItems(GroceryList groceries) {
		this.groceriesDB = groceries.getItems();
	}
}
