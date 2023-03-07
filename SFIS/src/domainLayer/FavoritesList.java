package domainLayer;

import java.util.ArrayList;
import java.util.List;

public class FavoritesList extends ItemManager{
	
	public FavoritesList(List<StoredItem> items) {
		super(items);
	}
	
	public List<StoredItem> itemDifferences(List<StoredItem> items) {
		List<StoredItem> overlap = this.getItems();
		
		for (StoredItem item: items) {
			int index = this.itemIndex(item);
			if (index < 0) 
				continue;
			
			StockableItem favoritedStock = overlap.get(index).getStockableItem();
			int currentAmount = Math.min(favoritedStock.getMax(), item.getStockableItem().getStock());
			favoritedStock.setStock(currentAmount);
		}
		
		return overlap;
	}

}
