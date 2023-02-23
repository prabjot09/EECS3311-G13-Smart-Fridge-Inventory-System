package domainLayer;

public class ContinuousStockableItem extends StockableItem {
	private static final int INCREMENT = 10;
	private static final String[] equivalents = {
			"Empty", "Very Low", "Low", "Half Full", "Mostly Full", "Near Full", "Full"
		};
	
	public ContinuousStockableItem() {
		
	}
	
	public ContinuousStockableItem(int amount) {
		this.setStock(amount);
		this.setMax(100);
	}
	
	@Override
	public void setStock(int val) {
		int floorAmt = Math.max(0, val);
		int cielAmt = Math.min(100, floorAmt);
		super.setStock(cielAmt);
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
		String desc = "" + this.getStock() + "%";
		return desc;
	}
	
	public String getStringEquivalent() {
		if (this.getStock() == 0)
			return equivalents[0];
		
		for (int i = 1; i < 6; i++) {
			if (this.getStock() < i*20) {
				return equivalents[i];
			}
		}
		
		return equivalents[equivalents.length - 1];
	}

}
