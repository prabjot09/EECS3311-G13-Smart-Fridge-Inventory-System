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
		// TODO: Implement update
		
		/*
		 0) If date has changed since last update, then must recalibrate (shift table and weight distribution factor)
		 
		 1a) Update existing items day-end and consumption/restocking (Consumption must be distributed, restocking is only for today)
		 	- 	Distrubution must be even. 
		 		Ex: 
		 			If current consumption over 4 days is: 1, 2, 2, 2.
		 			Need to distribute 5 over past 4 days.
		 			Resulted Increments: 1, 1, 1, 2
		 			Resulted Consumption: 3, 3, 3, 3
		 	- If item had been previously deleted, first set all of its values back to 0, then adjust quantities.
		 	- If deleted today, then use prev day + today's data to regenerate the day-end and resume with the distribution.
		 			
		 1b) Add new items into the table. (Create new Pair<FoodItem, ItemHistory> and ItemHistory must init distributed days, the other 6 days are just -1)
		 
		 2) Items that have been deleted 7+ days ago must be removed 
		 	- removed at end of day -> DAYEND = -1 (all other values static)
		 	- all subsequent days -> DAYEND = -1, RESTOCK = -1, CONSUMPTION = -1
		 */
		

		// TODO 2: If day changes while application is on (lastUpdate != today), then prevSession is changed to be equal to lastUpdate
		//         because this means that you had actually adjusted for any skipped days already since the lastUpdate
		
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
			int stockChange = item.getStockableItem().getStock() - history.getDayEndAmount(day);
			if (stockChange > 0) {
				history.increaseRestocking(day, stockChange);
			}
			else {
				history.distributeConsumption(day, periodOfUpdate - day, 0 - stockChange);
				System.out.println(item.getDescription() + ", " + periodOfUpdate);
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
		/* TODO: 	Check the current date and the last history update date.
		If date is same (current day) -> then take data directly.
		If data is different -> must shift data by days accordingly and must redistribute all of todays changes over skipped days evenly.
		
		*/
		 
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
	
	
	public boolean isModifiedYesterday() {
		boolean result = false;
		
		for (Pair<FoodItem, ItemHistory> entry: historyData) {
			if (entry.getB().getConsumptionAmount(1) > 0) {
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	
}
