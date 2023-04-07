package IntegrationTests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import IntegrationTests.ItemDBTest.StubFood1;
import IntegrationTests.ItemDBTest.StubFood2;
import domainLayer.FoodItem;
import domainLayer.FoodItem.CreationType;
import domainLayer.FoodItem.StockType;
import domainLayer.UserSettings;
import persistenceLayer.RealDB;

class UserSettingsDBTest {
	// Password and user values are machine dependant based on your own sql settingsn
	private String user = "root";
	private String password = "G-13-Excellent";
	private String url = "jdbc:mysql://localhost:3306/SIFSDB";
	public final String EXPIRY_THRESHOLD = "Expiry Warning Threshold";
	public final String SMART_FEATURE_ON = "Smart Feature Enabled Bit";
	public final String GROCERY_THRESHOLD = "Automated Grocery Addition Threshold";
	public Map<String,Integer> hold = new HashMap<>();
	public UserSettings setting = new UserSettings();
	
	@Test
	void settings() {
		hold.put(EXPIRY_THRESHOLD, 10);
		hold.put(SMART_FEATURE_ON, 1);
		hold.put(GROCERY_THRESHOLD, 30);
		setting.setAddGroceryListThreshold(30);
		setting.setExpirationWarningDays(10);
		setting.setSmartFeaturesEnabled(true);
		try {
		
			Connection con = DriverManager.getConnection(url, user, password);
			Statement createState = con.createStatement();
			createState.executeUpdate("drop table usersettings;");
			
			RealDB db = new RealDB(user,password);
			
			assertEquals(db.loadUserSettings().getExpirationWarningDays(), 5, "Default exp wrong");
			assertEquals(db.loadUserSettings().isSmartFeaturesEnabled(), false, "Default smart feature wrong");
			assertEquals(db.loadUserSettings().getAddGroceryListThreshold(), 20, "Default grocery threshold wrong");
			
			db.updateUserSettings(setting);
			
			assertEquals(db.loadUserSettings().getExpirationWarningDays(), 10, "updated exp wrong");
			assertEquals(db.loadUserSettings().isSmartFeaturesEnabled(), true, "updated smart feature wrong");
			assertEquals(db.loadUserSettings().getAddGroceryListThreshold(), 30, "updated grocery threshold wrong");
			
			
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
