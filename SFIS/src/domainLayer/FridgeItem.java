package domainLayer;

public class FridgeItem extends StoredItem{
	
	public FridgeItem() {
		
	}
	
	public FridgeItem(FridgeItem item) {
		this.setFoodItem(item.getFoodItem());
		this.setStockableItem(item.getStockableItem());
	}
	
	@Override
	public void executeIncrement(int val) {
		StockableItem stock = getStockableItem();
		stock.increment(val);
	}

	@Override
	public void executeDecrement(int val) {
		StockableItem stock = getStockableItem();
		stock.decrement(val);
	}

}
