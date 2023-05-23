package presentationLayer.itemListView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import domainLayer.ItemManager;
import domainLayer.StoredItem;
import presentationLayer.AppWindow;
import presentationLayer.GroceryListView;
import presentationLayer.swingExtensions.CustomBoxPanel;
import presentationLayer.swingExtensions.CustomButton;
import presentationLayer.swingExtensions.CustomPanel;
import presentationLayer.swingExtensions.GridConstraintsSpec;

public class ButtonEmbeddedListView extends JPanel implements ListView{
	
	private JPanel content;
	private ItemManager itemList;
	
	private boolean incrementable, decrementable;
	
	private GroceryListView groceries;
	private List<ButtonEmbeddedItemComponent> itemComponents;
	
	private int cols;
	
	public ButtonEmbeddedListView(ItemManager list, int cols) {
		itemList = list;
		itemComponents = new ArrayList<>();
		this.cols = cols;
		groceries = null;
		incrementable = true;
		decrementable = true;
		
		this.setBackground(new Color(60, 60, 60));
		this.setLayout(new BorderLayout());
		
		content = new CustomPanel(this.getBackground(), new GridLayout(1, cols));
		JScrollPane scroll = new JScrollPane(content);
		scroll.setBackground(this.getBackground());
		scroll.setViewportBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		scroll.setBorder(BorderFactory.createEmptyBorder());
		this.add(scroll);
		
		generateList(list.getItems());
	}
	
	public void generateList(List<StoredItem> items) {
		itemComponents = new ArrayList<>();
		int count = content.getComponentCount();
		for (int i = 0; i < count; i++) 
			content.remove(0);
		
		List<JPanel> cells = new ArrayList<>();
		for (int i = 0; i < cols; i++) {
			JPanel cell = new CustomPanel(content.getBackground(), new GridBagLayout());
			content.add(cell);
			cells.add(cell);
		}
		
		for (int i = 0; i < items.size(); i++) {
			ButtonEmbeddedItemComponent itemComponent = new ButtonEmbeddedItemComponent(this, items.get(i), content.getBackground()); 
			if (groceries == null)
				itemComponent.removeGroceryLink();
			itemComponent.setStockChangeMode(incrementable, decrementable);
			
			cells.get(i % cols).add(itemComponent, GridConstraintsSpec.stretchableFillConstraints(0, i / cols, 1, 1, GridBagConstraints.BOTH));
			itemComponents.add(itemComponent);
		}
		
		content.repaint();
		content.revalidate();
	}
	
	public void setStockChangeMode(boolean increment, boolean decrement) {
		incrementable = increment;
		decrementable = decrement;
		
		for (ButtonEmbeddedItemComponent item: itemComponents) {
			item.setStockChangeMode(increment, decrement);
		}
	}
	
	public void setGrocery(GroceryListView grocery) {
		groceries = grocery;
		this.generateList(itemList.getItems());
	}
	
	public void removeGroceryLink() {
		groceries = null;
		this.generateList(itemList.getItems());
	}

	public void updateItem(StoredItem item) {
		itemList.updateItem(item);	
	}

	public void removeItem(StoredItem item) {
		int confirm = JOptionPane.showConfirmDialog(AppWindow.getWindow(), 
                "Are you sure you want to remove " + item.getFoodItem().getName(), 
                "Item Removal Confirmation", JOptionPane.YES_NO_OPTION);
		if (confirm != 0) {
			return;
		}
		
		itemList.remove(item);
		generateList(itemList.getItems());
	}

	public void addToGrocery(StoredItem item) {
		groceries.visualAdd(item);		
	}

}
