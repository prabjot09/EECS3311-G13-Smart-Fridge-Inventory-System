package domainLayer;

public class DiscreteStockableItem extends StockableItem {
	private static final int INCREMENT = 1;
	private static final int MAXIMUM = 256;
	
	public DiscreteStockableItem() {
		
	}
	
	public DiscreteStockableItem(int amount) {
		this.setStock(amount);
	}
	
	public DiscreteStockableItem(DiscreteStockableItem stock) {
		this.setStock(stock.getStock());
		this.setMax(stock.getMax());
	}
	
	@Override
	public void setStock(int amount) {
		int amountZeroBound = Math.max(0, amount);
		super.setStock(amountZeroBound);
		super.setMax(Math.max(this.getMax(), amountZeroBound));
	}
	
	@Override
	public void setMax(int amount) {
		int amountZeroBound = Math.max(0, amount);
		int amountStockBound = Math.max(this.getStock(), amountZeroBound);
		super.setMax(amountStockBound);		
	}
	
	@Override
	public void increment() {
		this.setStock(Math.min(this.getStock() + INCREMENT, MAXIMUM));	
		
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
	
	@Override
	public DiscreteStockableItem copy() {
		return new DiscreteStockableItem(this);
	}

	@Override
	public StockableItem refillQuantity() {
		return new DiscreteStockableItem(this.getMax() - this.getStock());
	}
	
	@Override
	public boolean stockWithinBounds() {
		return this.getStock() <= MAXIMUM;
	}

	@Override
	public String getShortDesc() {
		return "" + this.getStock();
	}
}
