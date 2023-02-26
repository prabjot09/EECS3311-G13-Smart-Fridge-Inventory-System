package persistenceLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import domainLayer.FoodItem;
import domainLayer.FoodItem.StockType;
import domainLayer.FridgeItem;

public class RealDB {
	 String url = "jdbc:mysql://localhost:3306/SIFS";
		String user = "root";
		String password = "G-13-Excellent";
		String create = "Create Table if not exists fridgeitem" +
		"(name VARCHAR(255)," +
		"StockType INT," +
		"Amount INT," +
		"PRIMARY KEY ( name))";
	
		 public  void instantiateDB() {
		
		
		try {
			Connection con = DriverManager.getConnection(url,user,password);
			Statement createState = con.createStatement();
			createState.executeUpdate(create);
			
			} 
		catch ( SQLException e ) {
				 e . printStackTrace () ;
				 }
		 }
		 
		 public void addItem (FridgeItem Fridge) {
			 String name = Fridge.getFoodItem().getName();
			 int fridgeEnum;
			 int amount = Fridge.getStockableItem().getStock();
			 
			 if (Fridge.getFoodItem().getStockType() == StockType.values()[0]) {
				 fridgeEnum = 0;
			 }
			 else {
				 fridgeEnum = 1;
			 }
			 
			 String query = "insert into fridgeitem VALUES (?, ? , ?);";
			 
			 try {
				 Connection con = DriverManager.getConnection(url,user,password);
					PreparedStatement statement = con.prepareStatement(query);
					Statement createState = con.createStatement();
					createState.executeUpdate(create);
					statement.setInt(2,fridgeEnum);
					statement.setString(1,name);
					statement.setInt(3, amount);
					statement.executeUpdate();
					
					} 
				catch ( SQLException e ) {
						 e . printStackTrace () ;
						 }
				 
			 
		 }
}
