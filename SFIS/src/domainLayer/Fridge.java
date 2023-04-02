package domainLayer;

import java.util.*;

public class Fridge extends ItemManager{
	
	public Fridge(List<StoredItem> items) {
		super(items);
	}
	
	@Override
	public List<StoredItem> getItems() {
		List<StoredItem> items = prioritizeExpiring();
		return items;
	}
	
	public List<StoredItem> prioritizeExpiring() {
		List<StoredItem> nearExpiry = new ArrayList<StoredItem>();
		List<StoredItem> fine = new ArrayList<StoredItem>();
		
		for (StoredItem item: super.getItems()) {
			FridgeItem fItem = (FridgeItem) item;
			if (fItem.getExpDate() != null && fItem.isExpiring()) {
				nearExpiry.add(item);
			} else {
				fine.add(item);
			}
		}
		
		List<StoredItem> result = nearExpiry;
		for (StoredItem item: fine) {
			result.add(item);
		}
		
		this.setItems(result);
		return result;
	}
	
	public List<StoredItem> getExpiringItems() {
		List<StoredItem> items = new ArrayList<StoredItem>();
		for (StoredItem item: super.getItems()) {
			FridgeItem fItem = (FridgeItem) item;
			if (fItem.getExpDate() != null && fItem.isExpiring())
				items.add(item);
		}
		return items;
	}
	
}

