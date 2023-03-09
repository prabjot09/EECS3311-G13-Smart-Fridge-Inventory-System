package presentationLayer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;

import domainLayer.DBProxy;
import domainLayer.Fridge;
import domainLayer.ItemManager;
import domainLayer.StoredItem;
import presentationLayer.swingExtensions.CustomButton;
import presentationLayer.swingExtensions.CustomPanel;

public class CompressedListView extends JPanel implements ActionListener, ListView{
	
	private ItemManager inv;
	private JScrollPane scroll;
	private JList<String> list;
	private JButton incButton;
	private JButton decButton;
	private JButton remButton;
	private JButton groceryListButton;
	private List<StoredItem> displayItems;
	private DefaultListModel<String> stringItemList;
	
	private JPanel buttonPanel;
	private boolean buttonPanelFlag;

	public CompressedListView(ItemManager inv) {
		this.inv = inv;
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.displayItems = inv.getItems();
		
	    list = new JList<String>();
	    list.setBackground(Color.gray);
	    list.setFont(new Font("Arial", Font.BOLD, 24));
	    list.setPreferredSize(new Dimension(800, 220));
	    list.setBounds(0,300,1000,500);
	    
	    scroll = new JScrollPane(list);
	    scroll.setPreferredSize(new Dimension(820, 220));
	    this.setBackground(Color.black);
	    this.setPreferredSize(new Dimension(820, 400));
	    this.add(scroll);
	    
	    buttonPanel = new CustomPanel(Color.black, null);
	    this.add(buttonPanel);
	    
	    incButton = new CustomButton("Increment", this, 15);
	    buttonPanel.add(incButton);
	    
	    decButton = new CustomButton("Decrement", this, 15);
	    buttonPanel.add(decButton);
	    
	    remButton = new CustomButton("Remove", this, 15);
	    buttonPanel.add(remButton);
	    
	    groceryListButton = new CustomButton("Add to Grocery List", this, 15);
	    buttonPanel.add(groceryListButton);
	    
	    this.buttonPanelFlag = true;
	    this.generateList(displayItems);
	}
	
	public void generateList(List<StoredItem> items) {
		this.displayItems = items;
		DefaultListModel<String> updatedFridgeList = new DefaultListModel<String>();
		for (StoredItem item: this.displayItems) {
			updatedFridgeList.addElement(item.getDescription());
		}
		 
		stringItemList = updatedFridgeList;
		list.setModel(updatedFridgeList);
		list.setPreferredSize(new Dimension(800, 30 * stringItemList.size()));
		list.revalidate();
		 
		revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (list.getSelectedIndex() == -1) {
			JOptionPane.showMessageDialog(null, "Select an item please!", "Notice", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if (e.getSource() == incButton) {	
			int itemIndex = list.getSelectedIndex();
			StoredItem item = this.displayItems.get(itemIndex);
			item.executeIncrement();
			this.inv.updateItem(item);
			
			this.displayItems.set(itemIndex, inv.getItems().get(inv.itemIndex(item)));
			this.stringItemList.set(itemIndex, this.displayItems.get(itemIndex).getDescription());
			this.list.revalidate();
		}
		else if (e.getSource() == decButton) {	
			int itemIndex = list.getSelectedIndex();
			if (this.displayItems.get(itemIndex).getStockableItem().getStock() <= 0) {
				JOptionPane.showMessageDialog(null, "Item is at 0 stock", "Notice", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			StoredItem item = this.displayItems.get(itemIndex);
			item.executeDecrement();
			this.inv.updateItem(item);
			
			this.displayItems.set(itemIndex, inv.getItems().get(inv.itemIndex(item)));
			this.stringItemList.set(itemIndex, this.displayItems.get(itemIndex).getDescription());
			this.list.revalidate();
		}
		else if (e.getSource() == remButton) {
			int itemIndex = list.getSelectedIndex();
			this.inv.remove(this.displayItems.get(itemIndex));
			this.displayItems.remove(itemIndex);
			this.stringItemList.remove(itemIndex);
		}
		else if (e.getSource() == groceryListButton) {
			// Add grocery list insertion code
			
		}
	}
	
	public void addItem(StoredItem item) {
		displayItems.add(item);
	    stringItemList.addElement(item.getDescription());
	    list.setModel(stringItemList);
	    list.setPreferredSize(new Dimension(800, 30 * stringItemList.size()));
	    list.revalidate();
	}
	
	public void setButtonPanelFlag(boolean flag) {
		if (flag == buttonPanelFlag)
			return;
		
		buttonPanelFlag = flag;
		if (buttonPanelFlag) {
			this.add(buttonPanel);
		}
		else {
			this.remove(buttonPanel);
		}
		
		this.revalidate();
	}
	
	public StoredItem getSelectedItem() {
		int index = list.getSelectedIndex();
		
		if (index == -1) {
			return null;
		}
		return displayItems.get(index);
	}
	
	public void setListListener(ListSelectionListener listener) {
		list.addListSelectionListener(listener);
	}
}
