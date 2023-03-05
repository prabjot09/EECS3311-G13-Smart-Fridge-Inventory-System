package domainLayer;

import domainLayer.FoodItem.StockType;

public class StockableItemFactory {
	
	public static StockableItem createStockableItem(StockType type, int amount) {
		 if (type == StockType.CONTINUOUS) {
			 return new ContinuousStockableItem(amount);
		 }
		 else if (type == StockType.DISCRETE) {
			 return new DiscreteStockableItem(amount);
		 }
		 else {
			 return null;
		 }
	}
}
