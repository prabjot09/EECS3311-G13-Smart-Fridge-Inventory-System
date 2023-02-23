package domainLayer;

public class FridgeItem extends StoredItem{
	
	public FridgeItem() {
		
	}
	
	public FridgeItem(FridgeItem item) {
		this.setFoodItem(item.getFoodItem());
		this.setStockableItem(item.getStockableItem());
	}
	
	@Override
	public void executeIncrement() {
		StockableItem stock = getStockableItem();
		stock.increment();
	}

	@Override
	public void executeDecrement() {
		StockableItem stock = getStockableItem();
		stock.decrement();
	}

}
