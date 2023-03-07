package presentationLayer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import domainLayer.DBProxy;
import domainLayer.Fridge;
import domainLayer.ItemManager;
import domainLayer.StoredItem;

public class CompressedListView extends JPanel implements ActionListener, ListView{
	
	private ItemManager inv;
	private JScrollPane scroll;
	private JList<String> list;
	private JButton incButton;
	private JButton decButton;
	private JButton remButton;
	private List<StoredItem> displayItems;
	private DefaultListModel<String> stringItemList;

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
	    
	    JPanel buttonPanel = new JPanel();
	    buttonPanel.setBackground(Color.black);
	    this.add(buttonPanel);
	    
	    incButton = new JButton("Increment");
	    incButton.addActionListener(this);
	    incButton.setPreferredSize(new Dimension(200,50));
	    buttonPanel.add(incButton);
	    
	    decButton = new JButton("Decrement");
	    decButton.addActionListener(this);
	    decButton.setPreferredSize(new Dimension(200,50));
	    buttonPanel.add(decButton);
	    
	    remButton = new JButton("Remove");
	    remButton.addActionListener(this);
	    remButton.setPreferredSize(new Dimension(200, 50));
	    buttonPanel.add(remButton);
	    
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
			this.displayItems.get(itemIndex).executeIncrement();
			this.inv.updateItem(this.displayItems.get(itemIndex));
			this.stringItemList.set(itemIndex, this.displayItems.get(itemIndex).getDescription());
			this.list.revalidate();
		}
		else if (e.getSource() == decButton) {	
			int itemIndex = list.getSelectedIndex();
			if (this.displayItems.get(itemIndex).getStockableItem().getStock() <= 0) {
				JOptionPane.showMessageDialog(null, "Item is at 0 stock", "Notice", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			this.displayItems.get(itemIndex).executeDecrement();
			this.inv.updateItem(this.displayItems.get(itemIndex));
			this.stringItemList.set(itemIndex, this.displayItems.get(itemIndex).getDescription());
			this.list.revalidate();
		}
		else if (e.getSource() == remButton) {
			int itemIndex = list.getSelectedIndex();
			this.inv.remove(this.displayItems.get(itemIndex));
			this.displayItems.remove(itemIndex);
			this.stringItemList.remove(itemIndex);
		}
	}
	
	public void addItem(StoredItem item) {
		displayItems.add(item);
	    stringItemList.addElement(item.getDescription());
	    list.setModel(stringItemList);
	    list.setPreferredSize(new Dimension(800, 30 * stringItemList.size()));
	    list.revalidate();
	}
	
}
