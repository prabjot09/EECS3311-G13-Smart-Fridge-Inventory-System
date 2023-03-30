package domainLayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemHistory {
	public static int DAYEND = 0;
	public static int CONSUMPTION = 1;
	public static int RESTOCKING = 2;
	
	private List<Map<Integer, Integer>> itemHistory;
	
	
	public ItemHistory(List<Integer> data) {
		itemHistory = new ArrayList<>();
		
		for (int day = 0; day < 7; day++) {
			Map<Integer, Integer> dailyData = new HashMap<>();
			for (int type = 0; type < 3; type++) {
				dailyData.put(type, data.get(day*3 + type));
			}
			itemHistory.add(dailyData);
		}		
	}
	
	
	public int getDayEndAmount(int daysBack) {
		return itemHistory.get(daysBack).get(DAYEND);
	}
	
	
	public int getConsumptionAmount(int daysBack) {
		return itemHistory.get(daysBack).get(CONSUMPTION);
	}
	
	
	public int getRestockingAmount(int daysBack) {
		return itemHistory.get(daysBack).get(RESTOCKING);
	}
	
	
	public void setDayEnd(int daysBack, int amount) {
		itemHistory.get(daysBack).put(DAYEND, amount);
	}
	
	
	public void increaseConsumption(int daysBack, int increment) {
		int newAmount = getConsumptionAmount(daysBack) + increment;
		itemHistory.get(daysBack).put(CONSUMPTION, newAmount);
	}
	
	
	public void increaseRestocking(int daysBack, int increment) {
		int newAmount = getRestockingAmount(daysBack) + increment;
		itemHistory.get(daysBack).put(RESTOCKING, newAmount);
	}


	public void shiftDays(int daysSinceUpdate) {
		int originalStock = getDayEndAmount(0);
		
		for (int i = 0; i < daysSinceUpdate; i++) {
			Map<Integer, Integer> dayData = new HashMap<>();
			if (originalStock == -1) {
				dayData.put(DAYEND, -1);
				dayData.put(CONSUMPTION, -1);
				dayData.put(RESTOCKING, -1);
			}
			else {
				dayData.put(DAYEND, originalStock);
				dayData.put(CONSUMPTION, 0);
				dayData.put(RESTOCKING, 0);
			}
			
			itemHistory.remove(itemHistory.size() - 1);
			itemHistory.add(0, dayData);
		}
		
	}
	
	
	public void distributeConsumption(int day, int periodLength, int amount) {
		// TODO: Distribute the consumption. Distribution is longest continguous period starting from today without having any -1 entries in DAYEND.
		//       This period is upperboounded by given periodLength
		int distributionDepth = 0;
		int startPoint = day;
		for (int i = day; i < periodLength; i++) {
			if (itemHistory.get(i).get(DAYEND) == -1) {
				break;
			}
			
			distributionDepth += 1;
			if (startPoint == day && itemHistory.get(i).get(CONSUMPTION) < itemHistory.get(day).get(CONSUMPTION)) {
				startPoint = i;
			}
		}
		
		int amountLeft = amount;
		for (int i = day; i < distributionDepth + day; i++) {
			int amountPerDay = (int) Math.ceil( ((double) amountLeft) / (distributionDepth - i) );
			int dayIndex = ((startPoint + i - day) % distributionDepth) + day;
			
			increaseConsumption(dayIndex, amountPerDay);
			amountLeft -= amountPerDay;
		}
	}
	
	
	public boolean isEmpty() {
		boolean isEmpty = true;
		
		for (int i = 0; i < itemHistory.size(); i++) {
			isEmpty = isEmpty && (itemHistory.get(0).get(DAYEND) == -1);
		}
		
		return isEmpty;
	}
	
	
	public String toString() {
		return this.itemHistory.toString();
	}


	public static ItemHistory createEmptyHistory(int day) {
		List<Integer> data = new ArrayList<>();
		for (int i = 0; i < 21; i++) {
			data.add(-1);
		}
		
		for (int i = 0; i < day*3; i++) {
			data.set(i, 0);
		}
		return new ItemHistory(data);
	}
	
	public void recordItemDeletion(int day) {
		setDayEnd(day, -1);
		
		for (int i = 0; i < day; i++) {
			itemHistory.get(i).put(DAYEND, -1);
			itemHistory.get(i).put(CONSUMPTION, -1);
			itemHistory.get(i).put(RESTOCKING, -1);
		}
	}
}
