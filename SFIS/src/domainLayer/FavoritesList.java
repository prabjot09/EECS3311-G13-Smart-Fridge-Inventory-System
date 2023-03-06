package domainLayer;

import java.util.ArrayList;
import java.util.List;

public class FavoritesList extends ItemManager{
	
	public FavoritesList(List<StoredItem> items) {
		super(items);
	}
	
	public List<StoredItem> itemDifferences(List<StoredItem> items) {
		List<StoredItem> overlap = new ArrayList<StoredItem>();
		
		for (StoredItem item: items) {
			int index = this.itemIndex(item);
			if (index < 0) 
				continue;
			
			StoredItem copy = this.getItems().get(index).copy();
			int currentAmount = Math.max(item.getStockableItem().getStock(), copy.getStockableItem().getMax());
			copy.getStockableItem().setStock(currentAmount);
			
			overlap.add(copy);
		}
		
		return overlap;
	}

}
