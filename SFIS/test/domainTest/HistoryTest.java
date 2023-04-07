package domainTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import domainLayer.ApplicationClock;
import domainLayer.FoodItem;
import domainLayer.Fridge;
import domainLayer.FridgeItem;
import domainLayer.ItemHistory;
import domainLayer.Pair;
import domainLayer.StockableItem;
import domainLayer.StockableItemFactory;
import domainLayer.StoredItem;
import domainLayer.UserHistory;
import persistenceLayer.HistoryStubDB;

public class HistoryTest {
	
	private UserHistory curr;
	private UserHistory original;

	
	private Fridge makeFridgeFromHistory(UserHistory hist) {
		List<StoredItem> items = new ArrayList<>();
		for (int i = 0; i < hist.getData().size(); i++) {
			FoodItem food = hist.getData().get(i).getA();
			int stock = hist.getItemHistory(food.getName()).getDayEndAmount(0);
			
			if (stock == -1)
				continue;
			
		    StockableItem sItem = StockableItemFactory.createStockableItem(food.getStockType(), stock);
		    FridgeItem item = new FridgeItem();
		    item.setFoodItem(food);
		    item.setStockableItem(sItem);
		    items.add(item);
		}
		Fridge inv = new Fridge(items);
		return inv;
	}
	
	
	private UserHistory makeHistoryCopy(UserHistory hist) {
		List<Pair<FoodItem, ItemHistory>> copy = new ArrayList<>();
		for (int i = 0; i < hist.getData().size(); i++) {
			ItemHistory itemH = hist.getData().get(i).getB();
			List<Integer> ints = new ArrayList<>(21);
			for (int day = 0; day < 7; day++) {
				ints.add(0); ints.add(0); ints.add(0);
				ints.set(day*3 + ItemHistory.CONSUMPTION, itemH.getConsumptionAmount(day));
				ints.set(day*3 + ItemHistory.DAYEND, itemH.getRestockingAmount(day));
				ints.set(day*3 + ItemHistory.RESTOCKING, itemH.getDayEndAmount(day));
			}
			copy.add(new Pair<FoodItem, ItemHistory>(hist.getData().get(i).getA(), new ItemHistory(ints)));
		}
		return new UserHistory(copy, hist.getRecalibrationDate(), hist.getModificationDate());
	}
	
	
	@Test
	public void consumptionDistributionTest() {		
		ApplicationClock.initSimulatedClock();
		HistoryStubDB db = new HistoryStubDB();
		curr = new UserHistory(db.getHistoryData(), LocalDate.now(), LocalDate.now());
		original = makeHistoryCopy(curr);
		
		ApplicationClock.incrementClock();
		ApplicationClock.incrementClock();
		ApplicationClock.incrementClock();
		
		Fridge inv = makeFridgeFromHistory(curr);
		
		FridgeItem item = new FridgeItem();
		item.setFoodItem(original.getData().get(0).getA());
		int index = inv.itemIndex(item);
		
		StoredItem itemCopy = inv.getItems().get(index);
		for (int i = 0; i < 4; i++)
			itemCopy.executeIncrement();
		
		inv.updateItem(itemCopy);
		
		item = new FridgeItem();
		item.setFoodItem(original.getData().get(1).getA());
		index = inv.itemIndex(item);
		
		itemCopy = inv.getItems().get(index);
		for (int i = 0; i < 5; i++)
			itemCopy.executeDecrement();
		
		inv.updateItem(itemCopy);
		curr.updateHistory(inv, 0);
		
		assertEquals(curr.getData().size(), original.getData().size(), "Items are getting deleted/added during recalibration or update without change in inventory.");
		
		System.out.println(curr.getData().get(1).getB());
		assertEquals(curr.getData().get(0).getB().getConsumptionAmount(2), 0, "Setting consumption when user is restocking");
		assertEquals(curr.getData().get(0).getB().getConsumptionAmount(1), 0, "Setting consumption when user is restocking");
		assertEquals(curr.getData().get(0).getB().getConsumptionAmount(0), 0, "Setting consumption when user is restocking");
		assertEquals(curr.getData().get(0).getB().getRestockingAmount(0), 97, "Restocking remaining amount isn't occuring effectively.");
		
		assertEquals(curr.getData().get(1).getB().getConsumptionAmount(2), 1, "Distribution of consumption not working for second item for 2 days ago");
		assertEquals(curr.getData().get(1).getB().getConsumptionAmount(1), 2, "Distribution of consumption not working for second item for yesterday");
		assertEquals(curr.getData().get(1).getB().getConsumptionAmount(0), 2, "Distribution of consumption not working for second item for today");
	}
	
	
	@Test
	public void recalibrationTableShiftTest() {
		
		ApplicationClock.initSimulatedClock();
		HistoryStubDB db = new HistoryStubDB();
		curr = new UserHistory(db.getHistoryData(), LocalDate.now(), LocalDate.now());
		original = makeHistoryCopy(curr);
		
		ApplicationClock.incrementClock();
		
		curr.recalibrateHistory();
		
		assertEquals(curr.getData().size(), original.getData().size(), "Recalibration doesn't affect the number of history records");
		
		for (int day = 0; day < 5; day++) {
			for (int i = 0; i < original.getData().size(); i++) {
				String name = original.getData().get(i).getA().getName();
				assertEquals(name, curr.getData().get(i).getA().getName(), "Recalibration is affecting item history order or removes/adds records.");
				
				ItemHistory expected = original.getItemHistory(name);
				ItemHistory actual = curr.getItemHistory(name);
				
				assertEquals(expected.getConsumptionAmount(day), actual.getConsumptionAmount(day + 1), "Incorrect recalibration of consumption of " + name + "  for " + day + " days ago.");
				
			}
		}
	}

}
