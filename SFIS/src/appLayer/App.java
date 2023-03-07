package appLayer;

import domainLayer.DBProxy;
import domainLayer.FavoritesList;
import domainLayer.Fridge;
import persistenceLayer.RealDB;
import persistenceLayer.StubDB;
import presentationLayer.mainWindow;

public class App {
	private static App app;
	private Fridge inv;
	private DBProxy db;
	private FavoritesList favorites;
	
	private App() {
		
	}
	
	public static void main(String[] args) {
		app = new App();
		DBProxy.getInstance().setDB(new RealDB());
		
		app.db = DBProxy.getInstance();
		app.inv = new Fridge(app.db.loadItems());
		//app.favorites = new FavoritesList(app.db.loadFavoritedItems());
		new mainWindow();
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
	
	public FavoritesList getFavorites() {
		return app.favorites;
	}
}
