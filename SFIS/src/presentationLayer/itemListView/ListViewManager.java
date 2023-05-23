package presentationLayer.itemListView;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import domainLayer.Fridge;
import domainLayer.StoredItem;

public class ListViewManager{
	public enum StockChangeMode {
		INCREMENT_ONLY,
		DECREMENT_ONLY,
		INCREMENT_DECREMENT,
		NONE;
	}

	private List<ListView> views;
	
	private int currentViewIndex;
	
	public ListViewManager(List<ListView> views) {
		this.views = views;
		
		currentViewIndex = 0;
	}
	
	public ListView toggle() {
		currentViewIndex = (currentViewIndex + 1) % views.size();
		return views.get(currentViewIndex);
	}
	
	public ListView getCurrentView() {
		return views.get(currentViewIndex);
	}

	public void setViewLists(List<StoredItem> items) {
		for (ListView view: views) {
			view.generateList(items);
		}
	}
	
	public void setSizes(Dimension dimension) {
		for (ListView view: views) {
			((JPanel) view).setPreferredSize(dimension);
		}
	}

	public void setStockChangeMode(boolean incrementEnabled, boolean decrementEnabled) {
		for (ListView view: views) {
			view.setStockChangeMode(incrementEnabled, decrementEnabled);
		}
		
	}
}
