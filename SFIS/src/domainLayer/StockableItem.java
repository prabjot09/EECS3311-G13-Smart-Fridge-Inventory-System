package domainLayer;

public abstract class StockableItem {	
	private int amount;
	private int maxAmount;
	
	public abstract void increment();
	public abstract void decrement();
	public abstract String getDescription();
	
	public int getStock() {
		return this.amount;
	}
	
	public void setStock(int val) {
		this.amount = val;
	}
	
	public int getMax() {
		return this.maxAmount;
	}
	
	public void setMax(int val) {
		this.maxAmount = val;
	}

}
