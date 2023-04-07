package IntegrationTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import domainLayer.ApplicationClock;
import domainLayer.ItemHistory;
import domainLayer.UserHistory;
import domainLayer.UserSettings;
import persistenceLayer.HistoryStubDB;
import persistenceLayer.RealDB;

public class HistoryDBTest {
	private String user = "root";
	private String password = "your_password_here";
	private String url = "jdbc:mysql://localhost:3306/SIFSDB";
	public final String EXPIRY_THRESHOLD = "Expiry Warning Threshold";
	public final String SMART_FEATURE_ON = "Smart Feature Enabled Bit";
	public final String GROCERY_THRESHOLD = "Automated Grocery Addition Threshold";
	public Map<String,Integer> hold = new HashMap<>();
	public UserHistory history; 
	
	@Test
	public void loadUpdateHistoryTest() {
		HistoryStubDB stubData = new HistoryStubDB();
		
		ApplicationClock.initRealClock();
		history = new UserHistory(stubData.getHistoryData(), LocalDate.now(), LocalDate.now());
		UserHistory stubHistory = history;
		
		try {
			Connection con = DriverManager.getConnection(url, user, password);
			Statement createState = con.createStatement();
			createState.executeUpdate("drop table " + RealDB.HISTORY + ";");
			
			RealDB db = new RealDB(user, password);
			UserHistory temp = db.loadUserHistory();
			
			assertEquals(temp.getData().size(), 0, "Data not cleared correctly.");
			
			db.updateUserHistory(history);
			history = db.loadUserHistory();
			
			assertEquals(history.getData().size(), stubHistory.getData().size(), "Not enough history records for all items or records for non-existing items.");
			
			for (int i = 0; i < stubHistory.getData().size(); i++ ) {
				for (int day = 0; day < 7; day++) {
					String name = stubHistory.getData().get(i).getA().getName();
					ItemHistory loadedHistory = history.getItemHistory(name);
					ItemHistory actualHistory = stubHistory.getItemHistory(name);
					
					assertEquals(loadedHistory.getConsumptionAmount(day), actualHistory.getConsumptionAmount(day), "Incorrect recording of consumption of " + name + "  for " + day + " days ago.");
					assertEquals(loadedHistory.getRecentConsumption(day), actualHistory.getRecentConsumption(day), "Incorrect recording of consumption of " + name + "  for " + day + " days ago.");
					assertEquals(loadedHistory.getDayEndAmount(day), actualHistory.getDayEndAmount(day), "Incorrect recording of consumption of " + name + "  for " + day + " days ago.");
					
				}
			}
			
		} catch (Exception e) {
			
		}
	}
}
