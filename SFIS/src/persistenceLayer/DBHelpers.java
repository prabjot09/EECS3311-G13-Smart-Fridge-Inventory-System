package persistenceLayer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import domainLayer.FoodItem;
import domainLayer.FridgeItem;
import domainLayer.StockableItemFactory;
import domainLayer.FoodItem.CreationType;
import domainLayer.FoodItem.StockType;

public class DBHelpers {
//Helper method that contains builders for some of our items, useful for our db impelmentation where wwe pack and unpack from sql
	String url = "jdbc:mysql://localhost:3306/SIFSDB";
	public DBHelpers() {
		
	}
	
	public FoodItem foodItemBuilder(String name, int stockEnum, int creatEnum) {
		FoodItem item = new FoodItem();
		item.setName(name);
		item.setStockType(StockType.values()[stockEnum]);
		item.setCreator(CreationType.values()[creatEnum]);

		return item;
	}

	public FridgeItem fridgeItemBuilder(String name, int stockEnum, int amount, int creatEnum, LocalDate Date) {
		FoodItem newItem = foodItemBuilder(name, stockEnum, creatEnum);
		FridgeItem item = new FridgeItem();

		item.setFoodItem(newItem);
		item.setStockableItem(StockableItemFactory.createStockableItem(newItem.getStockType(), amount));
		item.setExpDate(Date);
		
		return item;

	}
	
	public void adder(String type, String user, String password, FridgeItem Fridge) {
		String name = Fridge.getFoodItem().getName();
		String queryInsert = "insert into " + type + " VALUES (?, ? , ?, ?, ?) " + "ON DUPLICATE KEY UPDATE amount = ?;";
		int fridgeEnum;
		int creationEnum;
		int amount = Fridge.getStockableItem().getStock();
		LocalDate date = Fridge.getExpDate();
		if (Fridge.getFoodItem().getStockType() == StockType.values()[0]) {
			fridgeEnum = 0;
		} else {
			fridgeEnum = 1;
		}

		if (Fridge.getFoodItem().getCreator() == CreationType.values()[0]) {
			creationEnum = 0;
		} else {
			creationEnum = 1;
		}

		try {
			Connection con = DriverManager.getConnection(url, user, password);

			PreparedStatement statement = con.prepareStatement(queryInsert);

			statement.setInt(2, fridgeEnum);
			statement.setString(1, name);
			statement.setInt(3, amount);
			statement.setInt(4, creationEnum);
			statement.setInt(6, amount);
			if (date == null) {
				statement.setDate(5, null);
			} else {
				statement.setDate(5, Date.valueOf(date));
			}
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
