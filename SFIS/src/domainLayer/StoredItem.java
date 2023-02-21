package domainLayer;

public abstract class StoredItem {
	private FoodItem foodDescription;
	private StockableItem stock;
	
	public abstract void executeIncrement(int val);
	public abstract void executeDecrement(int val);
	
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
		String desc = this.foodDescription.getName() + ": " + this.stock.getStock() + " units";
		return desc;
	}
}
