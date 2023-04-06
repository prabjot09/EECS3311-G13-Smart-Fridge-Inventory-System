package persistenceLayer;

import java.util.ArrayList;
import java.util.List;

import domainLayer.FoodItem;
import domainLayer.FridgeItem;
import domainLayer.Recipe;
import domainLayer.StockableItemFactory;
import domainLayer.StoredItem;
import domainLayer.FoodItem.StockType;

public class RecipeStubDB {
	
		private List<Recipe> RecipeDB = new ArrayList<Recipe>();
		private List<StoredItem> GC = new ArrayList<StoredItem>();
		private List<StoredItem> MS = new ArrayList<StoredItem>();
		
		
		public RecipeStubDB() {
			//Simple items we instatiate at runtime
			FoodItem food1 = new FoodItem();
			food1.setName("Brown Bread - Loafs");
			food1.setStockType(StockType.values()[1]);
			FridgeItem item1 = new FridgeItem();
			item1.setStockableItem(StockableItemFactory.createStockableItem(food1.getStockType(), 1));
			item1.setFoodItem(food1);
			GC.add(item1);
			
			FoodItem food2 = new FoodItem();
			food2.setName("Cheese - Slices");
			food2.setStockType(StockType.values()[1]);
			FridgeItem item2 = new FridgeItem();
			item2.setStockableItem(StockableItemFactory.createStockableItem(food2.getStockType(), 1));
			item2.setFoodItem(food2);
			GC.add(item2);
			
			FoodItem b = new FoodItem();
			b.setName("Butter - Sticks");
			b.setStockType(StockType.values()[1]);
			FridgeItem bi = new FridgeItem();
			bi.setStockableItem(StockableItemFactory.createStockableItem(b.getStockType(), 1));
			bi.setFoodItem(food2);
			GC.add(bi);
			
			List<String> GCRecipe = new ArrayList<String>();
			GCRecipe.add("Heat a pan on the stovetop");
			GCRecipe.add("Gently butter two slices of bread");
			GCRecipe.add("Toast bread on both sides then add cheese slices and combine bread slices");
			GCRecipe.add("Cut and serve!");
			
			Recipe r1 = (new Recipe("Grilled Cheese - Whole Wheat", GC));
			r1.setInstructions(GCRecipe);
			RecipeDB.add(r1);
			
			
			
			FoodItem food3 = new FoodItem();
			food3.setName("Mango Juice - Bottle");
			food3.setStockType(StockType.values()[1]);
			FridgeItem item3 = new FridgeItem();
			item3.setStockableItem(StockableItemFactory.createStockableItem(food3.getStockType(), 1));
			item3.setFoodItem(food3);
			MS.add(item3);
			
			FoodItem food4 = new FoodItem();
			food4.setName("Milk - Cartons");
			food4.setStockType(StockType.values()[1]);
			FridgeItem item4 = new FridgeItem();
			item4.setStockableItem(StockableItemFactory.createStockableItem(food4.getStockType(), 1));
			item4.setFoodItem(food4);
			MS.add(item4);
			
			FoodItem food5 = new FoodItem();
			food5.setName("Ice Cream - Pint");
			food5.setStockType(StockType.values()[1]);
			FridgeItem item5 = new FridgeItem();
			item5.setStockableItem(StockableItemFactory.createStockableItem(food5.getStockType(), 1));
			item5.setFoodItem(food5);
			MS.add(item5);
			
			List<String> MSRecipe = new ArrayList<String>();
			MSRecipe.add("Add a cup of milk, 3 tablespoons of ice cream, 2 tablespoons of crushed ice, and 250ml of mango juice to a blender");
			MSRecipe.add("Blend on high for 1-2 minutes");
			MSRecipe.add("Add ice cream and milk/mango juice until desired consistency is reached");
			MSRecipe.add("Pour and enjoy!");
			
			Recipe r2 = (new Recipe("Mango MilkShake", MS));
			r2.setInstructions(MSRecipe);
			RecipeDB.add(r2);
		}
		
		public List<Recipe> getRecipeDB() {
			return RecipeDB;
		}
		
	
	
	

}
