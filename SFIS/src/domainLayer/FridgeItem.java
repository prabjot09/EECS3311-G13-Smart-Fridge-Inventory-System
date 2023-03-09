package domainLayer;

import java.time.LocalDate;

public class FridgeItem extends StoredItem{
	
	private LocalDate expDate;
	
	public FridgeItem() {
		
	}
	
	public FridgeItem(FridgeItem item) {
		this.setFoodItem(item.getFoodItem());
		this.setStockableItem(item.getStockableItem().copy());
	}
	
	@Override
	public void executeIncrement() {
		StockableItem stock = getStockableItem();
		stock.increment();
	}

	@Override
	public void executeDecrement() {
		StockableItem stock = getStockableItem();
		stock.decrement();
	}
	
	@Override
	public FridgeItem copy() {
		return new FridgeItem(this);
	}
	
	public void setExpDate(LocalDate expDate) {
		this.expDate = expDate;
		
	}
	public LocalDate getExpDate() {
			return this.expDate;
	}

}
