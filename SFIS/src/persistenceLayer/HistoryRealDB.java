package persistenceLayer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domainLayer.FoodItem;
import domainLayer.ItemHistory;
import domainLayer.Pair;
import domainLayer.UserHistory;
import domainLayer.FoodItem.StockType;

public class HistoryRealDB {
	
	private String url = "jdbc:mysql://localhost:3306/SIFSDB";
	private String select = "use SIFSDB";
	String queryInsertHistory = "insert into userhistory (name, StockType, Day, DayEnd, Consumption, Restocking) VALUES (?, ? , ?, ?, ?, ?);";
	String selectFromHistory = "select name, StockType, Day, DayEnd, Consumption, Restocking from userhistory;";
	String createHistoryTable = "Create Table if not exists userhistory" + "(name VARCHAR(255)," + "StockType INT,"
			+ "Day INT," + "DayEnd INT," + "Consumption INT," + "Restocking INT," + "PRIMARY KEY (name, Day))";
	String updateHistoryDrop = "drop table userhistory;";
	
	String createDateTable = "Create Table if not exists lastaccess" + "(Recalibrate DATE," + "Modify DATE)";
	String updateDateDrop = "drop table lastaccess";
	String selectModificationDate = "select Modify from lastaccess";
	String selectRecalibrationDate = "select Recalibrate from lastaccess";
	String queryInsertDate = "insert into lastaccess (Recalibrate, Modify) VALUES (?, ?);";
	
	public HistoryRealDB() {
		
	}

	
	public List<Pair<FoodItem, ItemHistory>> loadHistory(String user, String password) {
		List<Pair<FoodItem, ItemHistory>> parsed = new ArrayList<>();
		
		try {
			Connection con = DriverManager.getConnection(url, user, password);
			Statement createState = con.createStatement();
			createState.execute(select);
			ResultSet rs = createState.executeQuery(selectFromHistory);

			Map<String, List<Integer>> data = new HashMap<>();
			Map<String, StockType> typeMap = new HashMap<>();
			
			while (rs.next()) {
				String name = rs.getString(1);
				StockType stockEnum = StockType.values()[rs.getInt(2)];
				int day = rs.getInt(3);
				int dayEnd = rs.getInt(4);
				int consumption = rs.getInt(5);
				int restocking = rs.getInt(6);
				
				if (data.get(name) == null) {
					List<Integer> listInit = new ArrayList<>();
					for (int i = 0; i < 21; i++) {
						listInit.add(0);
					}
					data.put(name, listInit);
					typeMap.put(name, stockEnum);
				}
				
				List<Integer> itemData = data.get(name);
				itemData.set(day*3 + ItemHistory.DAYEND, dayEnd);
				itemData.set(day*3 + ItemHistory.CONSUMPTION, consumption);
				itemData.set(day*3 + ItemHistory.RESTOCKING, restocking);
			}
			
			for (String name: data.keySet()) {
				FoodItem food = new FoodItem(name, typeMap.get(name));
				ItemHistory history = new ItemHistory(data.get(name));
				parsed.add(new Pair<FoodItem, ItemHistory>(food, history));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return parsed;
	}
	
	
	//On window close, our program updates ours ql database with teh current contents of the favourites list
		//drops the table whenver called, probably a better way to do this
	public void updateHistory(UserHistory history, String user, String password) {
		try {
			Connection con = DriverManager.getConnection(url, user, password);
			Statement createState = con.createStatement();
			createState.execute(select);

			createState.executeUpdate(updateHistoryDrop);
			createState.executeUpdate(createHistoryTable);
			
			for (Pair<FoodItem, ItemHistory> entry: history.getData()) {
				String name = entry.getA().getName();
				StockType stockType = entry.getA().getStockType();
				int stockEnum = Arrays.asList(StockType.values()).indexOf(stockType);
				

				for (int day = 0; day < 7; day++) {
					int dayEnd = entry.getB().getDayEndAmount(day);
					int consumption = entry.getB().getConsumptionAmount(day);
					int restocking = entry.getB().getRestockingAmount(day);
					
					PreparedStatement statement = con.prepareStatement(queryInsertHistory);
					statement.setString(1, name);
					statement.setInt(2, stockEnum);
					statement.setInt(3, day);
					statement.setInt(4, dayEnd);
					statement.setInt(5, consumption);
					statement.setInt(6, restocking);
					statement.executeUpdate();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public LocalDate getModificationDate(String user, String password) {
		return getDate(selectModificationDate, user, password);
	}
	
	public LocalDate getRecalibrationDate(String user, String password) {
		return getDate(selectRecalibrationDate, user, password);
	}
	
	
	public LocalDate getDate(String dateQuery, String user, String password) {
		LocalDate date = null;
		
		try {
			Connection con = DriverManager.getConnection(url, user, password);
			Statement createState = con.createStatement();
			createState.execute(dateQuery);
			
			ResultSet rs = createState.executeQuery(selectModificationDate);
			rs.next();
			
			try {
				date = rs.getDate(1).toLocalDate();
			} catch (SQLException e) {
				date = LocalDate.now();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return date;
	}
	
	
	public void updateHistoryAccessTimes(LocalDate recalibration, LocalDate modification, String user, String password) {
		try {
			Connection con = DriverManager.getConnection(url, user, password);
			Statement createState = con.createStatement();
			createState.execute(select);
			
			createState.executeUpdate(updateDateDrop);
			createState.executeUpdate(createDateTable);
			
			PreparedStatement statement = con.prepareStatement(queryInsertDate);
			statement.setDate(1, Date.valueOf(recalibration));
			statement.setDate(2, Date.valueOf(modification));
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
