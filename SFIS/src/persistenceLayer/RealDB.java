package persistenceLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import domainLayer.FavoritesList;

import domainLayer.FoodItem;
import domainLayer.FoodItem.CreationType;
import domainLayer.FoodItem.StockType;
import domainLayer.Fridge;
import domainLayer.FridgeItem;
import domainLayer.StockableItemFactory;
import domainLayer.StoredItem;

public class RealDB implements DB {

	/*FridgeStubDB dbPop = new FridgeStubDB();
	List<StoredItem> fridgePop = dbPop.loadItems();
	FavoritesStubDB dbfavPop = new FavoritesStubDB();
	List<StoredItem> favPop = dbfavPop.loadFavoritedItems();
	*/
	FridgeRealDB fridgeDB = new FridgeRealDB();
	ItemStubDB dbPop = new ItemStubDB();
	List<String> itemPop = dbPop.getDB();
	String firsttimeurl = "jdbc:mysql://localhost:3306/";
	String url = "jdbc:mysql://localhost:3306/SIFSDB";
	String user = "root";
	String password = "G-13-Excellent";
	String createDatabase = "create database if not exists SIFSDB";
	String createTable = "Create Table if not exists fridgeitem" + "(name VARCHAR(255)," + "StockType INT,"
			+ "Amount INT," + "CreationType INT," + "Date DATE DEFAULT NULL," + "PRIMARY KEY ( name))";
	String queryInsert = "insert into fridgeitem VALUES (?, ? , ?, ?, ?) " + "ON DUPLICATE KEY UPDATE amount = ?;";
	String createFavTable = "Create Table if not exists favitem" + "(name VARCHAR(255)," + "StockType INT,"
			+ "Amount INT," + "CreationType INT," + "Date DATE DEFAULT NULL," + "PRIMARY KEY ( name))";
	String createItemTable = "Create Table if not exists itemDB" + "(name VARCHAR(255)," + "PRIMARY KEY ( name))";
	String queryInsertItem = "insert into itemDB VALUES (?)" + "ON DUPLICATE KEY UPDATE name = ?;";
	String queryInsertFav = "insert into favitem VALUES (?, ? , ?, ?, ?) " + "ON DUPLICATE KEY UPDATE amount = ?;";
	String select = "use SIFSDB";
	String selectFrom = "select * from fridgeitem;";
	String selectFromFav = "select * from favitem;";
	String selectFromItem = "select * from itemDB;";
	String updateDrop = "drop table fridgeitem;";
	String updateFavDrop = "drop table favitem;";

	public RealDB(String user, String pass) throws SQLException {

			this.user = user;
			this.password = pass;
			
			Connection con = DriverManager.getConnection(firsttimeurl, user, password);
			Statement createState = con.createStatement();
			PreparedStatement statement = con.prepareStatement(queryInsertItem);
			createState.executeUpdate(createDatabase);
			System.out.println("Succesfully Created Database");

			createState.executeUpdate(select);
			createState.executeUpdate(createTable);
			createState.executeUpdate(createFavTable);
			createState.executeUpdate(createItemTable);
			
			for (int x = 0; x < itemPop.size(); x++) {
				String itemName = itemPop.get(x);
				statement.setString(1, itemName);
				statement.setString(2,itemName);
				statement.executeUpdate();
			}

		

	/*		for (int x = 0; x < fridgePop.size(); x++) {
				addItem(fridgePop.get(x));
			}
			
			for (int x = 0; x < favPop.size(); x++) {
				addItem(favPop.get(x));
			}
	*/
			
	}

	@Override
	public void addItem(StoredItem Fridge) {
		
		fridgeDB.addItem((FridgeItem) Fridge, user, password);

	}
	
	public void addFavItem(StoredItem Fridge) {
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
			

			PreparedStatement statement = con.prepareStatement(queryInsertFav);

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
		List<String> holdMatch = new ArrayList<String>();

		try {
			Connection con = DriverManager.getConnection(url, user, password);
			Statement createState = con.createStatement();
			createState.execute(select);

			ResultSet rs = createState.executeQuery(selectFromItem);

			while (rs.next()) {

				String dbName = rs.getString(1);
				if (dbName.toLowerCase().contains(name)) {
					holdMatch.add(dbName);
				}

			}

		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		return holdMatch;
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
				LocalDate Date = rs.getDate(5).toLocalDate();
				FridgeItem item = fridgeItemBuilder(Name, stockEnum, amount, creatEnum, Date);

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
		
		fridgeDB.updateFridge(fridge, user, password);

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
	
	

	@Override
	public List<StoredItem> loadFavoritedItems() {
		List<StoredItem> fav = new ArrayList<StoredItem>();
		try {
			Connection con = DriverManager.getConnection(url, user, password);
			Statement createState = con.createStatement();
			createState.execute(select);

			ResultSet rs = createState.executeQuery(selectFromFav);

			while (rs.next()) {

				String Name = rs.getString(1);
				int stockEnum = rs.getInt(2);
				int amount = rs.getInt(3);
				int creatEnum = rs.getInt(4);
				LocalDate Date = rs.getDate(5).toLocalDate();
				
				FridgeItem item = fridgeItemBuilder(Name, stockEnum, amount, creatEnum,Date);

				fav.add(item);
			}

		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		return fav;
	}

	@Override
	public void updateFavoritedItems(FavoritesList favorites) {
		try {
			Connection con = DriverManager.getConnection(url, user, password);
			Statement createState = con.createStatement();
	
	

			createState.executeUpdate(updateFavDrop);
			createState.executeUpdate(createFavTable);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		for (int x = 0; x < favorites.getItems().size(); x++) {
			StoredItem itemToAdd = favorites.getItems().get(x);

			addFavItem(itemToAdd);

		}
		
	}


}
