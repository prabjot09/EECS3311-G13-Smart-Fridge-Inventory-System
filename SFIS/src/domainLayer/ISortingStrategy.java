package domainLayer;

import java.util.List;

public interface ISortingStrategy {
	public List<StoredItem> sortItems(List<StoredItem> items);
}
