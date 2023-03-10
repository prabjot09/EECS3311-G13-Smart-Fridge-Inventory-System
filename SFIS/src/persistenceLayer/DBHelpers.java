package persistenceLayer;

import java.time.LocalDate;

import domainLayer.FoodItem;
import domainLayer.FridgeItem;
import domainLayer.StockableItemFactory;
import domainLayer.FoodItem.CreationType;
import domainLayer.FoodItem.StockType;

public class DBHelpers {

	public DBHelpers() {
		
	}
	
	public FoodItem foodItemBuilder(String name, int stockEnum, int creatEnum) {
		FoodItem item = new FoodItem();
		item.setName(name);
		item.setStockType(StockType.values()[stockEnum]);
		item.setCreator(CreationType.values()[creatEnum]);

		return item;
	}

	public FridgeItem fridgeItemBuilder(String name, int stockEnum, int amount, int creatEnum, LocalDate Date) {
		FoodItem newItem = foodItemBuilder(name, stockEnum, creatEnum);
		FridgeItem item = new FridgeItem();

		item.setFoodItem(newItem);
		item.setStockableItem(StockableItemFactory.createStockableItem(newItem.getStockType(), amount));
		item.setExpDate(Date);
		
		return item;

	}
	
}
