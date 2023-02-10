package domainLayer;

public class DiscreteStockableItem extends StockableItem {
	
	public DiscreteStockableItem() {
		
	}
	
	public DiscreteStockableItem(int amount) {
		this.setStock(Math.max(amount, 0));
		this.setMax(amount);
	}
	
	@Override
	public void increment(int val) {
		this.setStock(this.getStock() + val);	
		
		this.setMax(Math.max(this.getStock(), this.getMax()));
	}

	@Override
	public void decrement(int val) {
		this.setStock(Math.max(0, this.getStock() - val));		
	}
}
