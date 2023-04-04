package appLayer;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JOptionPane;

import domainLayer.ApplicationClock;
import domainLayer.DBProxy;
import domainLayer.FavoritesList;
import domainLayer.Fridge;
import domainLayer.GroceryList;
import domainLayer.SmartFeature;
import domainLayer.StoredItem;
import domainLayer.UserHistory;
import domainLayer.UserSettings;
import persistenceLayer.DB;
import persistenceLayer.RealDB;
import persistenceLayer.StubDB;
import presentationLayer.AppWindow;
import presentationLayer.DBLoginView;
import presentationLayer.HomePageWindow;
import presentationLayer.mainWindow;

public class App {
	private static App app;
	
	private DBLoginView login;
	
	private DBProxy db;
	
	private Fridge inv;
	private FavoritesList favorites;
	private GroceryList groceries;
	private UserHistory history;
	private UserSettings settings;
	
	private boolean pendingUserAdjustment;
	
	private App() {
		
	}
	
	public static void main(String[] args) {
		app = new App();
		
		App.getInstance().login = new DBLoginView();
	}
	
	public static App getInstance() {
		if (app == null) {
			app = new App();
		}
		
		return app;
	}
	
	public Fridge getInventory() {
		return app.inv;
	}
	
	public void setInventory(Fridge inv) {
		this.inv = inv;
	}
	
	public FavoritesList getFavorites() {
		return app.favorites;
	}
	
	public void setFavorites(FavoritesList favorites) {
		this.favorites = favorites;
	}
	
	public GroceryList getGroceryList() {
		return app.groceries;
	}
	
	public void setGroceryList(GroceryList groceries) {
		this.groceries = groceries;
	}
	
	public UserHistory getHistory() {
		return this.history;
	}
	
	public void setHistory(UserHistory userHistory) {
		this.history = userHistory;
	}
	
	public UserSettings getSettings() {
		return this.settings;
	}
	
	public void setSettings(UserSettings settings) {
		this.settings = settings;
	}
	
	public boolean isPendingUserAdjustment() {
		return this.pendingUserAdjustment;
	}
	
	
	public void initializeApplication(String user, String pass) {
		try {
			DBProxy.getInstance().setDB(new RealDB(user, pass));
		} catch (Exception e) {
			System.out.println("Failed login.");
			login.loginFail();
			e.printStackTrace();
			return;
		}
		
		app.db = DBProxy.getInstance();
		ApplicationClock.initRealClock();
		
		loadData();		
		
		login.setVisible(false);
		login.dispose();
		
		AppWindow.getWindow().openWindow();
		AppWindow.getWindow().loadNewView(new HomePageWindow());
		
		checkExpirations();
	}
	
	public void checkExpirations() {
		List<StoredItem> expired = app.getInstance().inv.getExpiringItems();
		String expirations = "";
		for (StoredItem item: expired) {
			expirations += "\n" + item.getFoodItem().getName();
		}
		
		if (expired.size() > 0) {
			JOptionPane.showMessageDialog(null, 
										  "Warning: The following items are nearing their expiry or have already been expired: " + expirations, 
										  "Warning", 
										  JOptionPane.WARNING_MESSAGE);
		}
	}

	public void saveData() {
		DBProxy.getInstance().updateFridge(inv);
		DBProxy.getInstance().updateGroceryItems(groceries);
		DBProxy.getInstance().updateFavoritedItems(favorites);
		history.updateHistory(inv, 0);
		DBProxy.getInstance().updateUserHistory(history);
		app.settings.saveToDatabase();
	}
	
	public void exportData(String tables, File file) {
		DBProxy.getInstance().exportData(tables, file);
	}

	public void loadData() {
		app.inv = new Fridge(DBProxy.getInstance().loadItems());
		app.favorites = new FavoritesList(DBProxy.getInstance().loadFavoritedItems());
		app.groceries = new GroceryList(DBProxy.getInstance().loadGroceryItems());
		app.history = DBProxy.getInstance().loadUserHistory();
		
		app.settings = new UserSettings();
		app.settings.loadFromDatabase();
		
		app.pendingUserAdjustment = false;
		
		if (DBProxy.getInstance().loadUserSettings().isSmartFeaturesEnabled() == true && app.history.daysSinceUpdated() > 1) {
			SmartFeature sf = new SmartFeature(inv.getItems());
			app.pendingUserAdjustment = true;
			app.inv = new Fridge(sf.performSmartFeature());
		}
	}
}
