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
//Databaswe for our favourites list, very similar to our other 2 databases, probably a better way to do this 
//as the logic is the same, can just pass a different select form the realdb maybe?
public class FavoritesRealDB {
	DBHelpers helper = new DBHelpers();
	String url = "jdbc:mysql://localhost:3306/SIFSDB";
	String select = "use SIFSDB";
	String queryInsertFav = "insert into favitem VALUES (?, ? , ?, ?, ?) " + "ON DUPLICATE KEY UPDATE amount = ?;";
	String selectFromFav = "select name, StockType, Amount, CreationType, Date from favitem;";
	String createFavTable = "Create Table if not exists favitem" + "(name VARCHAR(255)," + "StockType INT,"
			+ "Amount INT," + "CreationType INT," + "Date DATE DEFAULT NULL," + "PRIMARY KEY ( name))";
	String updateFavDrop = "drop table favitem;";

	//each method takes the user and password given at the program launch
	public FavoritesRealDB() {

	}

	//method to add a favorited item to the sql database
	public void addFavItem(StoredItem Fridge, String user, String password) {
		String type = "favitem";
		helper.adder(type, user, password, (FridgeItem) Fridge);

	}

	//loads all the items currently in our persistent storage into a list of storeditems
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
//On window close, our program updates ours ql database with teh current contents of the favourites list
	//drops the table whenver called, probably a better way to do this
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
