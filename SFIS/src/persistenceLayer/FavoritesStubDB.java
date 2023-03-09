package persistenceLayer;

import java.util.ArrayList;
import java.util.List;

import domainLayer.FavoritesList;
import domainLayer.FoodItem;
import domainLayer.FridgeItem;
import domainLayer.StockableItemFactory;
import domainLayer.StoredItem;
import domainLayer.FoodItem.StockType;

public class FavoritesStubDB {
	private List<StoredItem> favorites;
	
	public FavoritesStubDB() {
		favorites = new ArrayList<>();
		
		FoodItem food1 = new FoodItem();
		food1.setName("Milk - 3 Bags");
		food1.setStockType(StockType.values()[0]);
		FridgeItem item1 = new FridgeItem();
		item1.setStockableItem(StockableItemFactory.createStockableItem(food1.getStockType(), 100));
		item1.setFoodItem(food1);
		favorites.add(item1);
		
		FoodItem food2 = new FoodItem();
		food2.setName("Juice - Carton");
		food2.setStockType(StockType.values()[1]);
		FridgeItem item2 = new FridgeItem();
		item2.setStockableItem(StockableItemFactory.createStockableItem(food2.getStockType(), 2));
		item2.setFoodItem(food2);
		favorites.add(item2);
		
		FoodItem food3 = new FoodItem();
		food3.setName("Eggs - Single");
		food3.setStockType(StockType.values()[1]);
		FridgeItem item3 = new FridgeItem();
		item3.setStockableItem(StockableItemFactory.createStockableItem(food3.getStockType(), 15));
		item3.setFoodItem(food3);
		favorites.add(item3);
		
		FoodItem food4 = new FoodItem();
		food4.setName("Bananas");
		food4.setStockType(StockType.values()[1]);
		FridgeItem item4 = new FridgeItem();
		item4.setStockableItem(StockableItemFactory.createStockableItem(food4.getStockType(), 7));
		item4.setFoodItem(food4);
		favorites.add(item4);
		
	}

	public List<StoredItem> loadFavoritedItems() {
		return favorites;
	}

	public void updateFavoritedItems(FavoritesList favorites) {
		this.favorites = favorites.getItems();
	}

}
	