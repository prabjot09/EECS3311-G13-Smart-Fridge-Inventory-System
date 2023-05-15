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
import presentationLayer.MainFridgeView;

public class StubbedApp {	
	public static void main(String[] args) {
		App app = App.getInstance();
		
		DBProxy.getInstance().setDB(new StubDB());
		ApplicationClock.initRealClock();
		
		app.loadData();
		
		AppWindow.getWindow().openWindow();
		AppWindow.getWindow().loadNewView(new HomePageWindow());
	}
}
