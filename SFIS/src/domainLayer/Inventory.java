package domainLayer;

import java.util.*;

public interface Inventory {
	List<? extends StoredItem> search(String name);
	void add(StoredItem item);
	void remove(StoredItem item);
}
