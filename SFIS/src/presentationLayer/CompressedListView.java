package presentationLayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;

import appLayer.App;
import domainLayer.DBProxy;
import domainLayer.Fridge;
import domainLayer.FridgeItem;
import domainLayer.ItemManager;
import domainLayer.StoredItem;
import presentationLayer.swingExtensions.CustomBoxPanel;
import presentationLayer.swingExtensions.CustomButton;
import presentationLayer.swingExtensions.CustomPanel;

public class CompressedListView extends JPanel implements ActionListener, ListView {

	private ItemManager inv;
	private JScrollPane scroll;
	private JList<String> list;
	private JButton incButton;
	private JButton decButton;
	private JButton remButton;
	private JButton groceryListButton;
	private List<StoredItem> displayItems;
	private DefaultListModel<String> stringItemList;
	private GroceryListView groceryView;

	private JPanel buttonPanel;
	private boolean buttonPanelFlag;

	public CompressedListView(ItemManager inv) {
		this.inv = inv;

		this.setLayout(new GridBagLayout());
		this.setBackground(Color.black);
		this.displayItems = inv.getItems();
		
		GridBagConstraints c = new GridBagConstraints();

		list = new JList<String>();
		list.setBackground(Color.gray);
		list.setFont(new Font("Arial", Font.BOLD, 18));
		// list.setPreferredSize(new Dimension(800, 220));
		// list.setBounds(0,300,1000,500);

		scroll = new JScrollPane(list);
		scroll.setBorder(BorderFactory.createLineBorder(Color.white));
		// scroll.setPreferredSize(new Dimension(820, 220));
		
		//this.setPreferredSize(new Dimension(820, 400));
		JPanel viewPanel = new CustomPanel(Color.black, new BorderLayout(), 5);
		viewPanel.add(scroll);
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		this.add(viewPanel, c);

		buttonPanel = new CustomPanel(Color.black, new BorderLayout(), 5);
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(buttonPanel, c);

		JPanel adjustPanel = new CustomBoxPanel(Color.black, BoxLayout.X_AXIS);
		buttonPanel.add(adjustPanel, BorderLayout.LINE_END);
		
		incButton = new CustomButton("Incr", this, 10);
		adjustPanel.add(incButton);
		
		adjustPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		
		decButton = new CustomButton("Decr", this, 10);
		adjustPanel.add(decButton);

		adjustPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		
		remButton = new CustomButton("Remove", this, 10);
		adjustPanel.add(remButton);

		JPanel groceryItemPanel = new CustomPanel(Color.black, new BorderLayout());
		buttonPanel.add(groceryItemPanel , BorderLayout.LINE_START);
		
		groceryListButton = new CustomButton("Add to Grocery List", this, 10);
		groceryItemPanel.add(groceryListButton);

		this.buttonPanelFlag = true;
		this.generateList(displayItems);
	}
	
	public CompressedListView(ItemManager inv, GroceryListView groceryView) {
		this(inv);
		this.groceryView = groceryView;
	}

	public void generateList(List<StoredItem> items) {
		this.displayItems = items;
		DefaultListModel<String> updatedFridgeList = new DefaultListModel<String>();
		for (StoredItem item : this.displayItems) {
			FridgeItem newItem = (FridgeItem) item;
			updatedFridgeList.addElement(newItem.getDescription());
		}

		stringItemList = updatedFridgeList;
		list.setModel(updatedFridgeList);
		// list.setPreferredSize(new Dimension(800, 30 * stringItemList.size()));
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
		} else if (e.getSource() == decButton) {
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
		} else if (e.getSource() == remButton) {
			int itemIndex = list.getSelectedIndex();
			this.inv.remove(this.displayItems.get(itemIndex));
			this.displayItems.remove(itemIndex);
			this.stringItemList.remove(itemIndex);
		} else if (e.getSource() == groceryListButton) {
			int itemIndex = list.getSelectedIndex();
			try {
				App.getInstance().getGroceryList().add(this.displayItems.get(itemIndex));
				groceryView.visualAdd(this.displayItems.get(itemIndex));
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Item already exists within the grocery list", "Notice", JOptionPane.WARNING_MESSAGE);
			}

		}
	}

	public void addItem(StoredItem item) {
		displayItems.add(item);
		stringItemList.addElement(item.getDescription());
		list.setModel(stringItemList);
		// list.setPreferredSize(new Dimension(800, 30 * stringItemList.size()));
		list.revalidate();
	}

	public void setButtonPanelFlag(boolean flag) {
		if (flag == buttonPanelFlag)
			return;

		buttonPanelFlag = flag;
		if (buttonPanelFlag) {
			this.add(buttonPanel);
		} else {
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
