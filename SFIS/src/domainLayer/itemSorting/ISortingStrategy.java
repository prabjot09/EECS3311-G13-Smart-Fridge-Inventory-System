package domainLayer.itemSorting;

import java.util.List;

import domainLayer.StoredItem;

public interface ISortingStrategy {
	public List<StoredItem> sortItems(List<StoredItem> items);
}
