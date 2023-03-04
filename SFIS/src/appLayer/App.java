package appLayer;

import domainLayer.DBProxy;
import persistenceLayer.StubDB;
import presentationLayer.mainWindow;

public class App {
	public static void main(String[] args) {
		DBProxy.getInstance().setDB(new StubDB());
		new mainWindow();
	}
}
