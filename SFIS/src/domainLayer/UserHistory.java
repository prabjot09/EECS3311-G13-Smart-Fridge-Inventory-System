package domainLayer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserHistory {
	private List<Pair<FoodItem, ItemHistory>> historyData;
	private Map<String, ItemHistory> itemHistoryMapping;
	private LocalDate previousSession;
	private LocalDate lastUpdate;
	
	// If greater than 1, this means that user hasn't used the application in multiple days so we must distribute their food consumption evenly over this period.
	private int periodOfUpdate;
	
	public UserHistory(List<Pair<FoodItem, ItemHistory>> data, LocalDate lastUpdate) {
		this.historyData = data;
		this.itemHistoryMapping = new HashMap<>();
		this.previousSession = lastUpdate.plusDays(0);
		this.lastUpdate = lastUpdate;
		this.periodOfUpdate = 1;
		
		for(Pair<FoodItem, ItemHistory> entry: historyData) {
			this.itemHistoryMapping.put(entry.getA().getName(), entry.getB());
		}
		
		recalibrateHistory();
	}
	
	
	public List<Pair<FoodItem, ItemHistory>> getData() {
		return historyData;
	}
	
	
	public LocalDate getPreviousSession() {
		return previousSession;
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
	
	
	public void updateHistory(Fridge inv) { 
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
		if (ApplicationClock.getDate() != lastUpdate) {
			previousSession = lastUpdate.plusDays(0);
		}
		
		recalibrateHistory();
		
		for (StoredItem item: inv.getItems()) {
			ItemHistory history = itemHistoryMapping.get(item.getFoodItem().getName());
			
			// Add new items
			if (history == null) {
				history = ItemHistory.createEmptyHistory();
				historyData.add(new Pair<FoodItem, ItemHistory>(item.getFoodItem(), history));
				itemHistoryMapping.put(item.getFoodItem().getName(), history);
			}
			
			// restore if item was previously deleted or is newly created
			if (history.getDayEndAmount(0) == -1) {
				history.setDayEnd(0, 0);
				
				if (history.getConsumptionAmount(0) != -1) {
					history.setDayEnd(0, history.getDayEndAmount(1) + history.getRestockingAmount(0) - history.getRestockingAmount(0));
				} else {
					history.increaseConsumption(0, 1);
					history.increaseRestocking(0, 1);
				}
			}
			
			// adjust history quantities
			int stockChange = item.getStockableItem().getStock() - history.getDayEndAmount(0);
			if (stockChange > 0) {
				history.increaseRestocking(0, stockChange);
			}
			else {
				history.distributeConsumption(periodOfUpdate, 0 - stockChange);
			}
			history.setDayEnd(0, item.getStockableItem().getStock());
		}
		
		// Check for recently deleted items
		for (Pair<FoodItem, ItemHistory> entry: historyData) {
			StoredItem comparisonItem = new FridgeItem();
			comparisonItem.setFoodItem(entry.getA());
			
			if (entry.getB().getDayEndAmount(0) != -1 && inv.itemIndex(comparisonItem) == -1) {
				entry.getB().setDayEnd(0, -1);
			}
		}
		
		lastUpdate = ApplicationClock.getDate();
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
		int daysSinceUpdate = Math.min(7, (int) lastUpdate.datesUntil(today).count());
		int daysSinceLastSession = Math.min(7, (int) previousSession.datesUntil(today).count());
		
		this.periodOfUpdate = Math.max(1, daysSinceLastSession);
		
		if (daysSinceUpdate == 0) {
			return;
		}
		
		List<Pair<FoodItem, ItemHistory>> emptyEntries = new ArrayList<>();
		for (int i = 0; i < historyData.size(); i++) {
			ItemHistory history = historyData.get(i).getB();
			
			history.shiftDays(daysSinceUpdate);
			if (history.isEmpty()) {
				emptyEntries.add(historyData.get(i));
			}
		}
		
		for (int i = 0; i < emptyEntries.size(); i++) {
			historyData.remove(emptyEntries.get(i));
			itemHistoryMapping.remove(emptyEntries.get(i).getA().getName());
		}
		
	}
	
	
}
