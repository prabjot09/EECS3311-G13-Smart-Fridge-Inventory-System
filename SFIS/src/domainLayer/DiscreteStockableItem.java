package domainLayer;

public class DiscreteStockableItem extends StockableItem {
	
	public DiscreteStockableItem() {
		
	}
	
	public DiscreteStockableItem(int amount) {
		this.setStock(amount);
	}
	
	@Override
	public void increment(int val) {
		this.setStock(this.getStock() + val);		
	}

	@Override
	public void decrement(int val) {
		this.setStock(this.getStock() - val);		
	}
}
