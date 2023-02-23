package domainLayer;

public abstract class StoredItem {
	private FoodItem foodDescription;
	private StockableItem stock;
	
	public abstract void executeIncrement();
	public abstract void executeDecrement();
	
	public FoodItem getFoodItem() {
		return this.foodDescription;
	}
	
	public void setFoodItem(FoodItem description) {
		this.foodDescription = description;
	}
	
	public StockableItem getStockableItem() {
		return this.stock;
	}
	
	public void setStockableItem(StockableItem stock) {
		this.stock = stock;
	}
	
	public String getDescription() {
		String desc = this.foodDescription.getName() + ": " + this.stock.getDescription();
		return desc;
	}
}
