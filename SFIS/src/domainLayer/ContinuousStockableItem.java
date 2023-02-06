package domainLayer;

public class ContinuousStockableItem implements StockableItem {
	private int amount;
	
	public ContinuousStockableItem() {
		
	}
	
	public ContinuousStockableItem(int amount) {
		this.amount = amount;
	}
	
	@Override
	public void increment(int val) {
		this.amount = Math.min(this.amount + val, 100);
	}

	@Override
	public void decrement(int val) {
		this.amount = Math.max(this.amount - val, 0);
		
	}

}
