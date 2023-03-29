package persistenceLayer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import domainLayer.FoodItem;
import domainLayer.ItemHistory;
import domainLayer.Pair;
import domainLayer.FoodItem.StockType;

public class HistoryStubDB {
	private List<Pair<FoodItem, ItemHistory>> data;
	private LocalDate lastSession;
	
	public HistoryStubDB() {
		data = new ArrayList<>();
		lastSession = LocalDate.now();
		
		FoodItem food1 = new FoodItem();
		food1.setName("Milk - 3 Bags");
		food1.setStockType(StockType.values()[0]);
		Integer[] dataList = {3, 0, 0, 3, 2, 0, 5, 1, 0, 6, 0, 6, -1, -1, -1, -1, -1, -1, -1, -1, -1};
		ItemHistory history1 = new ItemHistory(Arrays.asList(dataList));
		data.add(new Pair<FoodItem, ItemHistory>(food1, history1));
		
		FoodItem food2 = new FoodItem();
		food2.setName("Juice - Carton");
		food2.setStockType(StockType.values()[1]);
		Integer[] dataList2 = {9, 2, 7, 5, 3, 0, 8, 0, 8, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
		ItemHistory history2 = new ItemHistory(Arrays.asList(dataList2));
		data.add(new Pair<FoodItem, ItemHistory>(food2, history2));
		
		FoodItem food3 = new FoodItem();
		food3.setName("Eggs - Single");
		food3.setStockType(StockType.values()[1]);
		Integer[] dataList3 = {10, 8, 0, 18, 0, 0, 18, 2, 0, 20, 4, 0, 24, 0, 24, -1, -1, -1, -1, -1, -1};
		ItemHistory history3 = new ItemHistory(Arrays.asList(dataList3));
		data.add(new Pair<FoodItem, ItemHistory>(food3, history3));
		
		FoodItem food4 = new FoodItem();
		food4.setName("Butter - Sticks");
		food4.setStockType(StockType.values()[1]);
		Integer[] dataList4 = {3, 1, 0, 4, 0, 4, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
		ItemHistory history4 = new ItemHistory(Arrays.asList(dataList4));
		data.add(new Pair<FoodItem, ItemHistory>(food4, history4));
		
	}
	
	public List<Pair<FoodItem, ItemHistory>> getHistoryData() {
		return this.data;
	}
	
	public LocalDate getLastSessionDate() {
		return this.lastSession;
	}
	
	public void setHistoryData(List<Pair<FoodItem, ItemHistory>> data) {
		this.data = data;
	}
	
	public void setLastSessionDate(LocalDate lastSession) {
		this.lastSession = lastSession;
	}
}
