package domainLayer;

public class ContinuousStockableItem extends StockableItem {
	private static final String[] levels = {
			"Empty", "Almost Empty", "Half Full", "Mostly Full", "Full"
		};
	public static final int INCREMENT = 25;
	public static final int MAXIMUM = 100;
	
	public ContinuousStockableItem() {
		
	}
	
	public ContinuousStockableItem(int amount) {
		this.setStock(amount);
		this.setMax(100);
	}
	
	public ContinuousStockableItem(ContinuousStockableItem stock) {
		this.setMax(stock.getMax());
		this.setStock(stock.getStock());
	}
	
	@Override
	public void setStock(int val) {
		int floorAmt = Math.max(0, val);
		int cielAmt = Math.min(100, floorAmt);
		super.setStock(cielAmt);
	}
	
	@Override
	public void setMax(int val) { 
		super.setMax(100);
	}
	
	@Override
	public void increment() {
		this.setStock(Math.min(this.getStock() + INCREMENT, 100));
	}

	@Override
	public void decrement() {
		this.setStock(Math.max(this.getStock() - INCREMENT, 0));	
	}
	
	@Override
	public String getDescription() {
		String equivalent = null;
		
		for (int i = 0; i < 5; i++) {
			if (this.getStock() <= i*INCREMENT) {
				equivalent = levels[i];
				break;
			}
		}
		
		return equivalent;
	}

	@Override
	public StockableItem copy() {
		return new ContinuousStockableItem(this);
	}

	@Override
	public StockableItem refillQuantity() {
		return new DiscreteStockableItem(1);
	}

	@Override
	public boolean stockWithinBounds() {
		return this.getStock() <= MAXIMUM;
	}

	@Override
	public String getShortDesc() {
		return this.getStock() + "%";
	}

}
