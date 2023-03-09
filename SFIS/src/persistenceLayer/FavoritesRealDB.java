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

import domainLayer.FavoritesList;
import domainLayer.FridgeItem;
import domainLayer.StoredItem;
import domainLayer.FoodItem.CreationType;
import domainLayer.FoodItem.StockType;

public class FavoritesRealDB {
	DBHelpers helper = new DBHelpers();
	String url = "jdbc:mysql://localhost:3306/SIFSDB";
	String select = "use SIFSDB";
	String queryInsertFav = "insert into favitem VALUES (?, ? , ?, ?, ?) " + "ON DUPLICATE KEY UPDATE amount = ?;";
	String selectFromFav = "select * from favitem;";
	String createFavTable = "Create Table if not exists favitem" + "(name VARCHAR(255)," + "StockType INT,"
			+ "Amount INT," + "CreationType INT," + "Date DATE DEFAULT NULL," + "PRIMARY KEY ( name))";
	String updateFavDrop = "drop table favitem;";

	public FavoritesRealDB() {

	}

	public void addFavItem(StoredItem Fridge, String user, String password) {
		String name = Fridge.getFoodItem().getName();
		int fridgeEnum;
		int creationEnum;
		int amount = Fridge.getStockableItem().getStock();
		LocalDate date = ((FridgeItem) Fridge).getExpDate();
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

			statement.setString(1, name);
			statement.setInt(2, fridgeEnum);
			statement.setInt(3, amount);
			statement.setInt(4, creationEnum);
			if (date == null) {
				statement.setDate(5, null);
			} else {
				statement.setDate(5, Date.valueOf(date));
			}
			statement.setInt(6, amount);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<StoredItem> loadFavoritedItems(String user, String password) {
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
				LocalDate Date;
				if (rs.getDate(5) == null) {
					Date = null;
				} else {
					Date = rs.getDate(5).toLocalDate();
				}

				FridgeItem item = helper.fridgeItemBuilder(Name, stockEnum, amount, creatEnum, Date);

				fav.add(item);
			}

		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		return fav;
	}

	public void updateFavoritedItems(FavoritesList favorites, String user, String password) {
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

			addFavItem(itemToAdd, user, password);

		}

	}
}
