package presentationLayer;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import domainLayer.Fridge;
import domainLayer.StoredItem;

public class ListViewManager{
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

	public void addItemToLists(StoredItem item) {
		for (ListView view: views) {
			view.addItem(item);
		}
	}
}
