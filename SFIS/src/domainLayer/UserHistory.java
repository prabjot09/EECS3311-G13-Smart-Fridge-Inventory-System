package domainLayer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserHistory {
	private List<Pair<FoodItem, ItemHistory>> historyData;
	private Map<String, ItemHistory> itemHistoryMapping;
	private LocalDate lastModified;
	private LocalDate lastRecalibrated;
	
	// If greater than 1, this means that user hasn't used the application in multiple days so we must distribute their food consumption evenly over this period.
	private int periodOfUpdate;
	
	public UserHistory(List<Pair<FoodItem, ItemHistory>> data, LocalDate recalibration, LocalDate modification) {
		this.historyData = data;
		this.itemHistoryMapping = new HashMap<>();
		this.lastModified = modification;
		this.lastRecalibrated = recalibration;
		this.periodOfUpdate = 1;
		
		for(Pair<FoodItem, ItemHistory> entry: historyData) {
			this.itemHistoryMapping.put(entry.getA().getName(), entry.getB());
		}
		
		recalibrateHistory();
	}
	
	
	public List<Pair<FoodItem, ItemHistory>> getData() {
		return historyData;
	}
	
	
	public LocalDate getRecalibrationDate() {
		return lastRecalibrated;
	}
	
	public LocalDate getModificationDate() {
		return lastModified;
	}
	
	public ItemHistory getItemHistory(String name) {
		
		ItemHistory result = null;
		
		FoodItem searchItem = new FoodItem();
		searchItem.setName(name);
		
		for (Pair<FoodItem, ItemHistory> itemEntry: historyData) {
			if (itemEntry.getA().sameAs(searchItem)) {
				result = itemEntry.getB();
				break;
			}
		}
		
		return result;
	}
	
	
	public void updateHistory(Fridge inv, int day) { 
		
		recalibrateHistory();
		
		LocalDate methodCallTime = ApplicationClock.getDate();
		boolean isModified = false;
		
		for (StoredItem item: inv.getItems()) {
			ItemHistory history = itemHistoryMapping.get(item.getFoodItem().getName());
			
			// Add new items
			if (history == null) {
				history = ItemHistory.createEmptyHistory(day);
				historyData.add(new Pair<FoodItem, ItemHistory>(item.getFoodItem(), history));
				itemHistoryMapping.put(item.getFoodItem().getName(), history);
				isModified = true;
			}
			
			// restore if item was previously deleted or is newly created
			if (history.getDayEndAmount(day) == -1) {
				history.setDayEnd(day, 0);
				isModified = true;
				
				if (history.getConsumptionAmount(day) != -1 && (day + 1 < 7)) {
					history.setDayEnd(day, history.getDayEndAmount(day + 1) + history.getRestockingAmount(day) - history.getConsumptionAmount(day));
				} else {
					history.increaseConsumption(day, 1);
					history.increaseRestocking(day, 1);
				}
			}
			
			// adjust history quantities
			int stockChange = item.getStockableItem().getStock() - history.getDayEndAmount(0);
			if (stockChange > 0) {
				history.increaseRestocking(day, stockChange);
			}
			else if (stockChange < 0) {
				history.distributeConsumption(day, periodOfUpdate - day, 0 - stockChange);
			}
			
			if (stockChange != 0) {
				history.setDayEnd(day, item.getStockableItem().getStock());
				isModified = true;
			}
			
			// propogate changes on a day to all subsequent days until today
			for (int i = 0; i < day; i++) {
				history.setDayEnd(i, history.getDayEndAmount(i) + stockChange);
			}
		}
		
		// Check for recently deleted items
		for (Pair<FoodItem, ItemHistory> entry: historyData) {
			StoredItem comparisonItem = new FridgeItem();
			comparisonItem.setFoodItem(entry.getA());
			
			if (entry.getB().getDayEndAmount(day) != -1 && inv.itemIndex(comparisonItem) == -1) {
				entry.getB().recordItemDeletion(day);
				isModified = true;
			}
		}
		
		if (isModified) {
			this.lastModified = methodCallTime;
		}
	}
	
	
	/*
	 * History is shifted each time a day passes since the last update to the table, but distribution factor
	 * is dependent only on days since last session to adjust for skipped days.
	 */
	public void recalibrateHistory() {
		 
		LocalDate today = ApplicationClock.getDate();
		int daysSinceRecalibration = Math.min(7, (int) lastRecalibrated.datesUntil(today).count());
		int daysSinceModification = Math.min(7, (int) lastModified.datesUntil(today).count());
		
		this.periodOfUpdate = Math.max(1, daysSinceModification);
		
		if (daysSinceRecalibration == 0) {
			return;
		}
		
		List<Pair<FoodItem, ItemHistory>> emptyEntries = new ArrayList<>();
		for (int i = 0; i < historyData.size(); i++) {
			ItemHistory history = historyData.get(i).getB();
			
			history.shiftDays(daysSinceRecalibration);
			if (history.isEmpty()) {
				emptyEntries.add(historyData.get(i));
			}
		}
		
		for (int i = 0; i < emptyEntries.size(); i++) {
			historyData.remove(emptyEntries.get(i));
			itemHistoryMapping.remove(emptyEntries.get(i).getA().getName());
		}
		
		lastRecalibrated = today;
	}
	
	
	public boolean isModifiedToday() {
		boolean result = false;
		
		for (Pair<FoodItem, ItemHistory> entry: historyData) {
			if (entry.getB().getConsumptionAmount(0) > 0) {
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	
	public int daysSinceUpdated() {
		return (int) lastModified.datesUntil(ApplicationClock.getDate()).count();
	}
	
}
