package domainLayer;

import java.util.*;

public interface Inventory {
	List<FridgeItem> search(String name);
	void add(FridgeItem item);
	void remove(FridgeItem item);
}
