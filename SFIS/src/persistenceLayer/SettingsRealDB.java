package persistenceLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domainLayer.FoodItem;
import domainLayer.ItemHistory;
import domainLayer.Pair;
import domainLayer.FoodItem.StockType;

public class SettingsRealDB {
	String url = "jdbc:mysql://localhost:3306/SIFSDB";
	String select = "use SIFSDB";
	String createSettingsTable = "Create Table if not exists usersettings " + "(name VARCHAR(255)," + "Value INT," 
			+ "PRIMARY KEY(name))";
	String updateSettingsDrop = "drop table usersettings;";
	String selectFromSettings = "select name, Value from usersettings;";
	String queryInsertSettings = "insert into usersettings (name, Value) VALUES (?, ?);";
	
	public final String EXRIPY_THRESHOLD = "Expiry Warning Threshold";
	public final String SMART_FEATURE_ON = "Smart Feature Enabled Bit";
	public final String GROCERY_THRESHOLD = "Automated Grocery Addition Threshold";
	
	public SettingsRealDB() {
		
	}
	
	
	public Map<String, Integer> loadSettings(String user, String password) {
		Map<String, Integer> dataMap = new HashMap<>();
		
		try {
			Connection con = DriverManager.getConnection(url, user, password);
			Statement createState = con.createStatement();
			createState.execute(select);
			ResultSet rs = createState.executeQuery(selectFromSettings);
			
			while (rs.next()) {
				String name = rs.getString(1);
				int value = rs.getInt(2);
				dataMap.put(name, value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dataMap;
	}
	
	
	public void updateSettings(String user, String password, Map<String, Integer> data) {
		try {
			Connection con = DriverManager.getConnection(url, user, password);
			Statement createState = con.createStatement();
			createState.execute(select);

			createState.executeUpdate(updateSettingsDrop);
			createState.executeUpdate(createSettingsTable);
			
			PreparedStatement statement1 = con.prepareStatement(queryInsertSettings);
			statement1.setString(1, EXRIPY_THRESHOLD);
			statement1.setInt(2, data.get(EXRIPY_THRESHOLD));
			statement1.executeUpdate();
			
			PreparedStatement statement2 = con.prepareStatement(queryInsertSettings);
			statement2.setString(1, GROCERY_THRESHOLD);
			statement2.setInt(2, data.get(GROCERY_THRESHOLD));
			statement2.executeUpdate();
			
			PreparedStatement statement3 = con.prepareStatement(queryInsertSettings);
			statement3.setString(1, SMART_FEATURE_ON);
			statement3.setInt(2, data.get(SMART_FEATURE_ON));
			statement3.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

}
