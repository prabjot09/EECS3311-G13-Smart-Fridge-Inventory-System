package domainLayer;

import java.util.*;

public interface Inventory {
	List<StoredItem> search(String name);
	void add(StoredItem item) throws Exception;
	void remove(StoredItem item);
}
