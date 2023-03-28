package domainLayer.itemSorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import domainLayer.StoredItem;

public class DepletedSorting implements ISortingStrategy{
	
	public List<StoredItem> sortItems(List<StoredItem> items) {
        List<StoredItem> sorted = new ArrayList<>(items); // create a copy of the original list
        Collections.sort(sorted, (item1, item2) -> { // sort items according to depletion level
            int depletionLevel1 = item1.getStockableItem().calculatePercent();
            int depletionLevel2 = item2.getStockableItem().calculatePercent();
            return depletionLevel1 - depletionLevel2;
        });
        return sorted;
    }
}
