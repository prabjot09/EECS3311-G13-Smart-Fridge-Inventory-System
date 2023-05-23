package domainTest;

import domainLayer.FoodItem;
import domainLayer.StockableItem;
import domainLayer.StoredItem;
import domainLayer.FoodItem.StockType;

class StubBase extends StoredItem {
	public void executeIncrement() { }
	public void executeDecrement() { }
	public StoredItem copy() { 
		StockableItem stock = this.getStockableItem();
		return new StubItemX(this.getFoodItem().getName(), stock.getStock(), stock.getMax()); 
	}		
}

class StubStock extends StockableItem {
	public void increment() { }
	public void decrement() { }
	public String getDescription() { return null; }
	public StockableItem copy() { return null; }
	public StockableItem refillQuantity() { return null; }
	public boolean stockWithinBounds() { return true; }
	public String getShortDesc() { return null; }
}

class StubItemX extends StubBase {
	int stock, max;
	public StubItemX(String name, int stock, int max) {
		this.setFoodItem(new FoodItem(name, StockType.DISCRETE));
		this.stock = stock; 
		this.max = max;
		this.setStockableItem(new StubStock() {
			public int getStock() { return StubItemX.this.stock; }
			public int getMax() { return StubItemX.this.max; }
			public void setStock(int a) { StubItemX.this.stock = a; }
			public void setMax(int a) { StubItemX.this.max = a; }
			public int calculatePercent() {
				if (StubItemX.this.max == 0)
					return 0;
				
				int percent = (StubItemX.this.stock * 100) / StubItemX.this.max;
				return percent;
			}
		});
	}
}