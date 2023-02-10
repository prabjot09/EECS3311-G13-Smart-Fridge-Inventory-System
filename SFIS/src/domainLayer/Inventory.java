package domainLayer;

import java.util.*;

public interface Inventory {
	public List<StoredItem> search(String name);
	public void add(StoredItem item) throws Exception;
	public void remove(StoredItem item);
}
