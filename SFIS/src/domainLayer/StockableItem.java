package domainLayer;

public abstract class StockableItem {	
	private int amount;
	
	public abstract void increment(int val);
	public abstract void decrement(int val);
	
	public int getStock() {
		return this.amount;
	}
	
	public void setStock(int val) {
		this.amount = val;
	}

}
