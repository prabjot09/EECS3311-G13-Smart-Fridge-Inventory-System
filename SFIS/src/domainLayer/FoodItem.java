package domainLayer;

public class FoodItem {
	public enum StockType {
		CONTINUOUS,
		DISCRETE
	}
	
	public enum CreationType {
		USER,
		PRESET
	}
	
	private String name;
	private StockType stockType;
	private CreationType creator;
	
	public FoodItem() {
		
	}
	
	public FoodItem(String name, StockType stockType) {
		this.name = name;
		this.stockType = stockType;
	}
	
	public FoodItem(String name, StockType stockType, CreationType creator) {
		this(name, stockType);
		this.creator = creator;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public StockType getStockType() {
		return this.stockType;
	}
	
	public void setStockType(StockType type) {
		this.stockType = type;
	}
	
	public CreationType getCreator() {
		return this.creator;
	}
	
	public void setCreator(CreationType creator) {
		this.creator = creator;
	}
}
