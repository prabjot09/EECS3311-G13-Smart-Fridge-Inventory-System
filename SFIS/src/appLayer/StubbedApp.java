package appLayer;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import domainLayer.ApplicationClock;
import domainLayer.DBProxy;
import domainLayer.FavoritesList;
import domainLayer.Fridge;
import domainLayer.GroceryList;
import domainLayer.StoredItem;
import persistenceLayer.RealDB;
import persistenceLayer.StubDB;
import presentationLayer.AppWindow;
import presentationLayer.DBLoginView;
import presentationLayer.HomePageWindow;
import presentationLayer.mainWindow;

public class StubbedApp {	
	public static void main(String[] args) {
		App app = App.getInstance();
		
		DBProxy.getInstance().setDB(new StubDB());
		ApplicationClock.initRealClock();
		
		app.setInventory(new Fridge(DBProxy.getInstance().loadItems()));
		app.setFavorites(new FavoritesList(DBProxy.getInstance().loadFavoritedItems()));
		app.setGroceryList(new GroceryList(DBProxy.getInstance().loadGroceryItems()));
		app.setHistory(DBProxy.getInstance().loadUserHistory());
		
		AppWindow.getWindow().openWindow();
		AppWindow.getWindow().loadNewView(new HomePageWindow());
	}
}