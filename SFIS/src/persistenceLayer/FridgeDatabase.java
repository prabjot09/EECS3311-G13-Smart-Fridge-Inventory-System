package persistenceLayer;

import java.util.ArrayList;
import java.util.List;

import domainLayer.ContinuousStockableItem;
import domainLayer.DiscreteStockableItem;
import domainLayer.FoodItem;
import domainLayer.Fridge;
import domainLayer.FridgeItem;
import domainLayer.StoredItem;

public class FridgeDatabase {
	private List<StoredItem> FridgeDB = new ArrayList<StoredItem>();
	
	
	public FridgeDatabase() {
		FridgeItem firstFI = new FridgeItem();
		FoodItem firstFoI = new FoodItem();
		firstFoI.setName("Milk - 3 Bags");
		firstFI.setStockableItem(new ContinuousStockableItem(12));
		firstFI.setFoodItem(firstFoI);
		FridgeDB.add(firstFI);
		
		FridgeItem secondFI = new FridgeItem();
		FoodItem secondFoI = new FoodItem();
		secondFoI.setName("Juice - Carton");
		secondFI.setStockableItem(new DiscreteStockableItem(1));
		secondFI.setFoodItem(secondFoI);
		FridgeDB.add(secondFI);
		
		FridgeItem thirdFI = new FridgeItem();
		FoodItem thirdFoI = new FoodItem();
		thirdFoI.setName("Eggs - Single");
		thirdFI.setStockableItem(new DiscreteStockableItem(12));
		thirdFI.setFoodItem(thirdFoI);
		FridgeDB.add(thirdFI);
		
		FridgeItem fourthFI = new FridgeItem();
		FoodItem fourthFoI = new FoodItem();
		fourthFoI.setName("Butter - Sticks");
		fourthFI.setStockableItem(new DiscreteStockableItem(4));
		fourthFI.setFoodItem(fourthFoI);
		FridgeDB.add(fourthFI);
		
	}
	
	
	public void setFridge(Fridge fridge) {
		FridgeDB = fridge.getFridgeItems();
	}
	
	
	public void addItem(FridgeItem item) {
		FridgeDB.add(item);
	}
	
	public List<StoredItem> loadItems() {
		return FridgeDB;
	}
}
