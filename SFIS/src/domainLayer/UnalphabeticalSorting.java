package domainLayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UnalphabeticalSorting implements ISortingStrategy{

	public List<StoredItem> sortItems(List<StoredItem> items) {
	    List<StoredItem> sorted = new ArrayList<>(items);
	    Collections.sort(sorted, new Comparator<StoredItem>() {
	        public int compare(StoredItem item1, StoredItem item2) {
	            String name1 = item1.getFoodItem().getName();
	            String name2 = item2.getFoodItem().getName();
	            return name2.compareTo(name1);
	        }
	    });
	    return sorted;
	}
}