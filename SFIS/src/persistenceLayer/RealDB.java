package persistenceLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domainLayer.FoodItem;
import domainLayer.FoodItem.CreationType;
import domainLayer.FoodItem.StockType;
import domainLayer.Fridge;
import domainLayer.FridgeItem;
import domainLayer.StockableItemFactory;
import domainLayer.StoredItem;

public class RealDB implements DB {
	FridgeDatabase dbPop = new FridgeDatabase();
	List<StoredItem> fridgePop = dbPop.loadItems();
	String firsttimeurl = "jdbc:mysql://localhost:3306/";
	String url = "jdbc:mysql://localhost:3306/SIFSDB";
	String user = "root";
	String password = "G-13-Excellent";
	String createDatabase = "create database if not exists SIFSDB";
	String createTable = "Create Table if not exists fridgeitem" + "(name VARCHAR(255)," + "StockType INT,"
			+ "Amount INT," + "CreationType INT," + "PRIMARY KEY ( name))";
	String queryInsert = "insert into fridgeitem VALUES (?, ? , ?, ?) " + "ON DUPLICATE KEY UPDATE amount = ?;";
	String select = "use SIFSDB";
	String selectFrom = "select * from fridgeitem;";

	public RealDB() {

		try {
			Connection con = DriverManager.getConnection(firsttimeurl, user, password);
			Statement createState = con.createStatement();
			createState.executeUpdate(createDatabase);
			System.out.println("Succesfully Created Database");

			createState.executeUpdate(select);
			createState.executeUpdate(createTable);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			Connection con = DriverManager.getConnection(url, user, password);
			Statement createState = con.createStatement();

			for (int x = 0; x < fridgePop.size(); x++) {
				addItem(fridgePop.get(x));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void addItem(StoredItem Fridge) {
		String name = Fridge.getFoodItem().getName();
		int fridgeEnum;
		int creationEnum;
		int amount = Fridge.getStockableItem().getStock();

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
			Statement createState = con.createStatement();

			PreparedStatement statement = con.prepareStatement(queryInsert);

			statement.setInt(2, fridgeEnum);
			statement.setString(1, name);
			statement.setInt(3, amount);
			statement.setInt(4, creationEnum);
			statement.setInt(5, amount);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<String> findMatchingFoods(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StoredItem> loadItems() {
		List<StoredItem> fridge = new ArrayList<StoredItem>();
		try {
			Connection con = DriverManager.getConnection(url, user, password);
			Statement createState = con.createStatement();
			createState.execute(select);

			ResultSet rs = createState.executeQuery(selectFrom);

			while (rs.next()) {

				String Name = rs.getString(1);
				int stockEnum = rs.getInt(2);
				int amount = rs.getInt(3);
				int creatEnum = rs.getInt(4);

				FridgeItem item = fridgeItemBuilder(Name, stockEnum, amount, creatEnum);

				fridge.add(item);
			}

		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		return fridge;
	}

	@Override
	public void updateFridge(Fridge fridge) {
		for (int x = 0; x < fridge.getItems().size(); x++) {
			StoredItem itemToAdd = fridge.getItems().get(x);

			addItem(itemToAdd);

		}

	}

	public FridgeItem fridgeItemBuilder(String name, int stockEnum, int amount, int creatEnum) {
		FoodItem newItem = new FoodItem();
		FridgeItem item = new FridgeItem();
		newItem.setName(name);
		newItem.setStockType(StockType.values()[stockEnum]);
		newItem.setCreator(CreationType.values()[creatEnum]);
		item.setFoodItem(newItem);
		item.setStockableItem(StockableItemFactory.createStockableItem(newItem.getStockType(), amount));

		return item;

	}

}
