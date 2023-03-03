package presentationLayer;

import java.util.List;

import javax.swing.JPanel;

import domainLayer.StoredItem;

public interface ListView{
	
	public abstract void generateList(List<StoredItem> items);
	public abstract void addItem(StoredItem item);

}
