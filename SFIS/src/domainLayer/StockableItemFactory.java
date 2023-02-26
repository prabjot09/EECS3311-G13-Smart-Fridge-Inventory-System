package domainLayer;

import domainLayer.FoodItem.StockType;

public class StockableItemFactory {
	
	public static StockableItem createStockableItem(int typeIndex, int amount) {
		 StockType type = StockType.values()[typeIndex];
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
