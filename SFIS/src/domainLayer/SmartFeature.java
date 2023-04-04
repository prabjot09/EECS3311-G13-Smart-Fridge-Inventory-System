package domainLayer;

import java.util.ArrayList;
import java.util.List;
import appLayer.App;
import domainLayer.FoodItem.StockType;

public class SmartFeature {
    private final List<StoredItem> items;
    
    public SmartFeature(List<StoredItem> items) {
        this.items = items;
    }

    public List<StoredItem> performSmartFeature() {
        UserHistory history = App.getInstance().getHistory();
        List<StoredItem> updatedItems = new ArrayList<>();

        for (StoredItem item : items) {
            int daysSinceUpdate = history.daysSinceUpdated();
            int averageConsumed = getAverageConsumed(item, history, daysSinceUpdate);
            int newAmount = calculateNewAmount(item, averageConsumed, daysSinceUpdate);
            updateStock(item, newAmount);

            updatedItems.add(item);
        }

        return updatedItems;
    }

    private int getAverageConsumed(StoredItem item, UserHistory history, int days) {
        List<Integer> recentConsumption = history.getItemHistory(item.getFoodItem().getName()).getRecentConsumption(1);
        int totalConsumption = 0;
        int count = 0;
        for (int consumption : recentConsumption) {
            if (consumption < 0) {
                break;
            }
        	totalConsumption += consumption;
            count++;
        }
        return count > 0 ? totalConsumption / count : 0;
    }

    private int calculateNewAmount(StoredItem item, int averageConsumed, int daysSinceUpdate) {
        int stock = item.getStockableItem().getStock();
        int consumed = averageConsumed* (daysSinceUpdate > 0 ? daysSinceUpdate : 1);
        return Math.max(0, stock - consumed);
    }

    private void updateStock(StoredItem item, int newAmount) {
        StockType stocktype = item.getFoodItem().getStockType();

        if (stocktype == StockType.CONTINUOUS) {
            int roundedAmount = Math.round(newAmount / 25f) * 25;
            if (roundedAmount > 100) {
                item.getStockableItem().setStock(100);
            } else {
                item.getStockableItem().setStock(roundedAmount);
            }
        } else {
            item.getStockableItem().setStock(newAmount);
        }
    }
}
