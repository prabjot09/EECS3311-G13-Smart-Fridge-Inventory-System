package persistenceLayer;

import java.util.ArrayList;
import java.util.List;

import domainLayer.ContinuousStockableItem;
import domainLayer.DiscreteStockableItem;
import domainLayer.FoodItem;
import domainLayer.FoodItem.StockType;
import domainLayer.Fridge;
import domainLayer.FridgeItem;
import domainLayer.StockableItemFactory;
import domainLayer.StoredItem;

public class FridgeDatabase {
	private List<StoredItem> FridgeDB = new ArrayList<StoredItem>();
	
	public FridgeDatabase() {
		//Fridge items we isntataite at runtime
		FridgeItem firstFI = new FridgeItem();
		FoodItem firstFoI = new FoodItem();
		firstFoI.setName("Milk - 3 Bags");
		firstFoI.setStockType(StockType.values()[0]);
		firstFI.setStockableItem(StockableItemFactory.createStockableItem(firstFoI.getStockType(), 75));
		firstFI.setFoodItem(firstFoI);
		FridgeDB.add(firstFI);
		
		FridgeItem secondFI = new FridgeItem();
		FoodItem secondFoI = new FoodItem();
		secondFoI.setName("Juice - Carton");
		secondFoI.setStockType(StockType.values()[1]);
		secondFI.setStockableItem(StockableItemFactory.createStockableItem(secondFoI.getStockType(), 1));
		secondFI.setFoodItem(secondFoI);
		FridgeDB.add(secondFI);
		
		FridgeItem thirdFI = new FridgeItem();
		FoodItem thirdFoI = new FoodItem();
		thirdFoI.setName("Eggs - Single");
		thirdFoI.setStockType(StockType.values()[1]);
		thirdFI.setStockableItem(StockableItemFactory.createStockableItem(thirdFoI.getStockType(), 12));
		thirdFI.setFoodItem(thirdFoI);
		FridgeDB.add(thirdFI);
		
		FridgeItem fourthFI = new FridgeItem();
		FoodItem fourthFoI = new FoodItem();
		fourthFoI.setName("Butter - Sticks");
		fourthFoI.setStockType(StockType.values()[1]);
		fourthFI.setStockableItem(StockableItemFactory.createStockableItem(fourthFoI.getStockType(), 4));
		fourthFI.setFoodItem(fourthFoI);
		FridgeDB.add(fourthFI);
	}
	
	
	public void setFridge(Fridge fridge) {
		FridgeDB = fridge.getItems();
	}
	
	
	public void addItem(FridgeItem item) {
		FridgeDB.add(item);
	}
	
	public List<StoredItem> loadItems() {
		return FridgeDB;
	}
}
