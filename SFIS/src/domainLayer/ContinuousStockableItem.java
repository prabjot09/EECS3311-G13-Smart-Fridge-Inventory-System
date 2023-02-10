package domainLayer;

public class ContinuousStockableItem extends StockableItem {
	
	public ContinuousStockableItem() {
		
	}
	
	public ContinuousStockableItem(int amount) {
		int floorAmt = Math.max(0, amount);
		int cielAmt = Math.min(100, floorAmt);
		this.setStock(cielAmt);
		this.setMax(100);
	}
	
	@Override
	public void increment(int val) {
		this.setStock(Math.min(this.getStock() + val, 100));
	}

	@Override
	public void decrement(int val) {
		this.setStock(Math.max(this.getStock() - val, 0));
		
	}

}
