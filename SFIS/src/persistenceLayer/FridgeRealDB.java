package persistenceLayer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import domainLayer.FridgeItem;
import domainLayer.StoredItem;
import domainLayer.FoodItem.CreationType;
import domainLayer.FoodItem.StockType;
import domainLayer.Fridge;

public class FridgeRealDB {
//Class where we handle all the logic for communicating to the fridge table of our database
	String createTable = "Create Table if not exists fridgeitem" + "(name VARCHAR(255)," + "StockType INT,"
			+ "Amount INT," + "CreationType INT," + "Date DATE DEFAULT NULL," + "PRIMARY KEY ( name))";
	String updateDrop = "drop table fridgeitem;";
	String url = "jdbc:mysql://localhost:3306/SIFSDB";
	String queryInsert = "insert into fridgeitem VALUES (?, ? , ?, ?, ?) " + "ON DUPLICATE KEY UPDATE amount = ?;";
	String select = "use SIFSDB";
	String selectFrom = "SELECT name, stocktype, amount, creationtype, date from fridgeitem;";
	DBHelpers helper = new DBHelpers();

	public FridgeRealDB() {

	}

//adds fridge items to our db
	public void addItem(FridgeItem Fridge, String user, String password) {
		String name = Fridge.getFoodItem().getName();
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

//on window close of our program, updates the fridge table
	// like our other classes it drops the table so not ideal
	public void updateFridge(Fridge fridge, String user, String password) {
		try {
			Connection con = DriverManager.getConnection(url, user, password);
			Statement createState = con.createStatement();

			createState.executeUpdate(updateDrop);
			createState.executeUpdate(createTable);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (int x = 0; x < fridge.getItems().size(); x++) {
			FridgeItem itemToAdd = (FridgeItem) fridge.getItems().get(x);

			addItem(itemToAdd, user, password);

		}
	}
//loads the fridgeitems from our table onto a storeditemlist for use in our program
	public List<StoredItem> loadItems(String user, String password) {
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
				LocalDate Date;
				if (rs.getDate(5) == null) {
					Date = null;
				} else {
					Date = rs.getDate(5).toLocalDate();
				}
				FridgeItem item = helper.fridgeItemBuilder(Name, stockEnum, amount, creatEnum, Date);

				fridge.add(item);
			}

		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		return fridge;

	}
}
