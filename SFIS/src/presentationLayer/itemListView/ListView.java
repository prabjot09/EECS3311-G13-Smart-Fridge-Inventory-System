package presentationLayer.itemListView;

import java.util.List;

import javax.swing.JPanel;

import domainLayer.StoredItem;
import presentationLayer.GroceryListView;
import presentationLayer.itemListView.ListViewManager.StockChangeMode;

public interface ListView{
	
	public abstract void generateList(List<StoredItem> items);
	public abstract void setStockChangeMode(boolean increment, boolean decrement);
	public abstract void setGrocery(GroceryListView grocery);
	public abstract void removeGroceryLink();
	public abstract void updateItem(StoredItem item);

}
