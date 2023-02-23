package domainLayer;

public class DiscreteStockableItem extends StockableItem {
	private static final int INCREMENT = 1;
	
	public DiscreteStockableItem() {
		
	}
	
	public DiscreteStockableItem(int amount) {
		this.setStock(Math.max(amount, 0));
		this.setMax(amount);
	}
	
	@Override
	public void increment() {
		this.setStock(this.getStock() + INCREMENT);	
		
		this.setMax(Math.max(this.getStock(), this.getMax()));
	}

	@Override
	public void decrement() {
		this.setStock(Math.max(0, this.getStock() - INCREMENT));		
	}
	
	@Override
	public String getDescription() {
		String desc = "" + this.getStock() + " units";
		return desc;
	}
}
