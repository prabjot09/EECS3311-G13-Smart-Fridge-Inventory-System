package appLayer;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import domainLayer.DBProxy;
import domainLayer.FavoritesList;
import domainLayer.Fridge;
import domainLayer.GroceryList;
import domainLayer.StoredItem;
import persistenceLayer.DB;
import persistenceLayer.RealDB;
import persistenceLayer.StubDB;
import presentationLayer.AppWindow;
import presentationLayer.DBLoginView;
import presentationLayer.mainWindow;

public class App {
	private static App app;
	
	private Fridge inv;
	private DBProxy db;
	private FavoritesList favorites;
	private DBLoginView login;
	private GroceryList groceries;
	
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
	
	public void initializeApplication(String user, String pass) {
		try {
			DBProxy.getInstance().setDB(new RealDB(user, pass));
		} catch (Exception e) {
			System.out.println("Failed login.");
			login.loginFail();
			return;
		}
		
		app.db = DBProxy.getInstance();
		app.inv = new Fridge(app.db.loadItems());
		app.favorites = new FavoritesList(app.db.loadFavoritedItems());
		app.groceries = new GroceryList(app.db.loadGroceryItems());
		
		login.setVisible(false);
		login.dispose();
		
		AppWindow.getWindow().openWindow();
		AppWindow.getWindow().loadNewView(new mainWindow());
		
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
	}
}
