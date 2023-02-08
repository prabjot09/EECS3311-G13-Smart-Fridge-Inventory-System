package domainLayer;

public class FridgeItem extends StoredItem{
	
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
