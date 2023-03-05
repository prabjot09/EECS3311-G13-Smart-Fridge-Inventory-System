package persistenceLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import domainLayer.FoodItem;
import domainLayer.FoodItem.CreationType;
import domainLayer.FoodItem.StockType;
import domainLayer.Fridge;
import domainLayer.FridgeItem;
import domainLayer.StoredItem;

public class RealDB implements DB {
	FridgeDatabase dbPop = new FridgeDatabase();
	List<StoredItem> fridgePop = dbPop.loadItems();
	 String url = "jdbc:mysql://localhost/";
		String user = "root";
		String password = "G-13-Excellent";
		String createDatabase = "create database if not exists SIFSDB";
		String createTable = "Create Table if not exists fridgeitem" +
		"(name VARCHAR(255)," +
		"StockType INT," +
		"Amount INT," +
		"CreationType INT," +
		"PRIMARY KEY ( name))";
		 String queryInsert = "insert into fridgeitem VALUES (?, ? , ?, ?) "
		 		+ "ON DUPLICATE KEY UPDATE name = ?;";
		 String select = "use SIFSDB";
		 
		 public RealDB() {
		
		
		try {
			Connection con = DriverManager.getConnection(url,user,password);
			Statement createState = con.createStatement();
			createState.executeUpdate(createDatabase);
			System.out.println("Succesfully Created Database");
			
			createState.executeUpdate(select);
			createState.executeUpdate(createTable);
			con.close();
			} 
		catch ( SQLException e ) {
				 e . printStackTrace () ;
				 }
		
		
		try {
			Connection con = DriverManager.getConnection(url,user,password);
			Statement createState = con.createStatement();
			createState.executeUpdate(select);
			PreparedStatement setupDB = con.prepareStatement(queryInsert);
			
			for (int x = 0; x < fridgePop.size(); x++) {
				addItem(fridgePop.get(x));
			}
		}
		catch ( SQLException e ) {
			 e . printStackTrace () ;
			 }
	
		 }
		 
		 @Override
		 public void addItem (StoredItem Fridge) {
			 String name = Fridge.getFoodItem().getName();
			 int fridgeEnum;
			 int creationEnum;
			 int amount = Fridge.getStockableItem().getStock();
			 
			 if (Fridge.getFoodItem().getStockType() == StockType.values()[0]) {
				 fridgeEnum = 0;
			 }
			 else {
				 fridgeEnum = 1;
			 }
			 
			 if (Fridge.getFoodItem().getCreator() == CreationType.values()[0]) {
				 creationEnum = 0;
			 }
			 else {
				 creationEnum = 1;
			 }
			 
			
			 
			 try {
				 Connection con = DriverManager.getConnection(url,user,password);
					Statement createState = con.createStatement();
					createState.executeUpdate(select);
					PreparedStatement statement = con.prepareStatement(queryInsert);
							
					statement.setInt(2,fridgeEnum);
					statement.setString(1,name);
					statement.setInt(3, amount);
					statement.setInt(4, creationEnum);
					statement.setString(5,name);
					statement.executeUpdate();
					
					} 
				catch ( SQLException e ) {
						 e . printStackTrace () ;
						 }
				 
			 
		 }

		@Override
		public List<String> findMatchingFoods(String name) {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public List<StoredItem> loadItems() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void updateFridge(Fridge fridge) {
			// TODO Auto-generated method stub
			
		}
}
