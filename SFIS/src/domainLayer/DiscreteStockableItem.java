package domainLayer;

public class DiscreteStockableItem implements StockableItem {
	private int amount;
	
	public DiscreteStockableItem() {
		
	}
	
	public DiscreteStockableItem(int amount) {
		this.amount = amount;
	}
	
	@Override
	public void increment(int val) {
		this.amount += val;		
	}

	@Override
	public void decrement(int val) {
		this.amount -= val;		
	}
	
}
