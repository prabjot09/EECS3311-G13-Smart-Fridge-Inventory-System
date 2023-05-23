package persistenceLayer;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Date;

import domainLayer.FavoritesList;

import domainLayer.FoodItem;
import domainLayer.FoodItem.CreationType;
import domainLayer.FoodItem.StockType;
import domainLayer.Fridge;
import domainLayer.FridgeItem;
import domainLayer.GroceryList;
import domainLayer.Recipe;
import domainLayer.StockableItemFactory;
import domainLayer.StoredItem;
import domainLayer.UserHistory;
import domainLayer.UserSettings;

public class RealDB implements DB {
//implementation of our real database, alot to go over here, many STrings where we hold sql commands to call
	// instantiate our 4 Databases where we deal with the fridge, teh favourties
	// list, the grocerylist, and teh list of items we
	// can add
	public static String FRIDGE = "fridgeitem";
	public static String FAVORITES = "favitem";
	public static String GROCERIES = "groceryitem";
	public static String HISTORY = "userhistory";
	public static String DATE = "lastaccess";
	public static String SETTINGS = "usersettings";
	
	FridgeRealDB fridgeDB = new FridgeRealDB();
	FavoritesRealDB favDB = new FavoritesRealDB();
	GroceryRealDB grocDB = new GroceryRealDB();
	HistoryRealDB historyDB = new HistoryRealDB();
	DBImportExport fileXfer = new DBImportExport();
	SettingsRealDB settingsDB = new SettingsRealDB();
	ItemStubDB dbPop = new ItemStubDB();
	RecipeStubDB rdbPop = new RecipeStubDB();
	List<String> itemPop = dbPop.getDB();
	
	// bunch of sql commands
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
	String createGroceryTable = "Create Table if not exists groceryitem" + "(name VARCHAR(255)," + "StockType INT,"
			+ "Amount INT," + "CreationType INT," + "Date DATE DEFAULT NULL," + "PRIMARY KEY ( name))";
	String createHistoryTable = "Create Table if not exists userhistory" + "(name VARCHAR(255)," + "StockType INT,"
			+ "Day INT," + "DayEnd INT," + "Consumption INT," + "Restocking INT," + "PRIMARY KEY (name))";
	String createDateTable = "Create Table if not exists lastaccess" + "(Recalibrate DATE," + "Modify DATE)";
	String createSettingsTable = "Create Table if not exists " + SETTINGS + "(name VARCHAR(255)," + "Value INT," 
			+ "PRIMARY KEY(name))";
	String queryInsertItem = "insert into itemDB VALUES (?)" + "ON DUPLICATE KEY UPDATE name = ?;";
	String queryInsertFav = "insert into favitem VALUES (?, ?, ?, ?, ?) " + "ON DUPLICATE KEY UPDATE amount = ?;";
	String select = "use SIFSDB";
	String selectFrom = "select * from fridgeitem;";
	String selectFromFav = "select * from favitem;";
	String selectFromItem = "select * from itemdb;";
	String updateDrop = "drop table fridgeitem;";
	String updateFavDrop = "drop table favitem;";

	// on isntantiation of the class, we pass the given user and password.
	// This user and password is whatever the user setup their sql user and password
	// to be
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
		createState.executeUpdate(createGroceryTable);
		createState.executeUpdate(createHistoryTable);
		createState.executeUpdate(createDateTable);
		createState.executeUpdate(createSettingsTable);
		//populates our item table everytiem the program is ran, can probably do a way to check if htis has been done so
		//we dont have to every time
		for (int x = 0; x < itemPop.size(); x++) {
			String itemName = itemPop.get(x);
			statement.setString(1, itemName);
			statement.setString(2, itemName);
			statement.executeUpdate();
		}

	}

	//all these add items just call from our 3 databases, passing the user and password along
	//maybe a static implementation isntead for itr3?
	@Override
	public void addItem(StoredItem Fridge) {

		fridgeDB.addItem((FridgeItem) Fridge, user, password);

	}

	public void addFavItem(StoredItem Fridge) {

		favDB.addFavItem(Fridge, user, password);

	}

	public void addGrocItem(StoredItem Fridge) {
		grocDB.addGroceryItem((FridgeItem) Fridge, user, password);
	}

	//our lone meaty method in this class, used to search through our db of preset items
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
				if (dbName.toLowerCase().contains(name.toLowerCase())) {
					holdMatch.add(dbName);
				}

			}

		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		return holdMatch;
	}

	//our loads and updates for our 3 databases classes
	@Override
	public List<StoredItem> loadItems() {
		return fridgeDB.loadItems(user, password);
	}

	@Override
	public void updateFridge(Fridge fridge) {

		fridgeDB.updateFridge(fridge, user, password);

	}

	@Override
	public List<StoredItem> loadFavoritedItems() {
		return favDB.loadFavoritedItems(user, password);
	}

	@Override
	public void updateFavoritedItems(FavoritesList favorites) {

		favDB.updateFavoritedItems(favorites, user, password);

	}

	@Override
	public List<StoredItem> loadGroceryItems() {
		return grocDB.loadGroceryItems(user, password);
	}

	@Override
	public void updateGroceryItems(GroceryList groceries) {

		grocDB.updateGroceryItems(groceries, user, password);

	}

	@Override
	public UserHistory loadUserHistory() {
		LocalDate modDate = historyDB.getModificationDate(user, password);
		LocalDate recalibrationDate = historyDB.getRecalibrationDate(user, password);
		UserHistory history = new UserHistory(historyDB.loadHistory(user, password), modDate, recalibrationDate);
		return history;
	}

	@Override
	public void updateUserHistory(UserHistory history) {
		historyDB.updateHistory(history, user, password);
		historyDB.updateHistoryAccessTimes(history.getRecalibrationDate(), history.getModificationDate(), user, password);
		
	}
	
	public void exportDB(String tables, File file) {
		try {
			fileXfer.DBExport(user, password, tables, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void importDB(File file) {
		try {
			fileXfer.DBImport(user, password, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateUserSettings(UserSettings settings) {
		Map<String, Integer> data = new HashMap<>();
		data.put(settingsDB.EXRIPY_THRESHOLD, settings.getExpirationWarningDays());
		data.put(settingsDB.GROCERY_THRESHOLD, settings.getAddGroceryListThreshold());
		data.put(settingsDB.SMART_FEATURE_ON, settings.isSmartFeaturesEnabled() ? 1 : 0);
		data.put(settingsDB.FAVORITES_THRESHOLD, settings.getFavoritesListThreshold());
		settingsDB.updateSettings(user, password, data);
		
	}

	@Override
	public UserSettings loadUserSettings() {
		UserSettings loadedSettings = new UserSettings();
		Map<String, Integer> data = settingsDB.loadSettings(user, password);
		if (data.size() == 0)
			return UserSettings.generateDefaultSettings();
		
		loadedSettings.setAddGroceryListThreshold(data.get(settingsDB.GROCERY_THRESHOLD));
		loadedSettings.setExpirationWarningDays(data.get(settingsDB.EXRIPY_THRESHOLD));
		loadedSettings.setSmartFeaturesEnabled(data.get(settingsDB.SMART_FEATURE_ON) == 1 ? true : false);
		loadedSettings.setFavoritesListThreshold(data.get(settingsDB.FAVORITES_THRESHOLD));
		return loadedSettings;
	}
	
	public List<Recipe> getRecipeDB(){
		return rdbPop.getRecipeDB();
	}

}
