package domainLayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DepletedSorting implements ISortingStrategy{
	
	public List<StoredItem> sortItems(List<StoredItem> items) {
        List<StoredItem> sorted = new ArrayList<>(items); // create a copy of the original list
        Collections.sort(sorted, (item1, item2) -> { // sort items according to depletion level
            int depletionLevel1 = item1.getStockableItem().getMax() - item1.getStockableItem().getStock();
            int depletionLevel2 = item2.getStockableItem().getMax() - item2.getStockableItem().getStock();
            return depletionLevel2 - depletionLevel1;
        });
        return sorted;
    }
}
