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
import presentationLayer.ListViewManager.StockChangeMode;
import presentationLayer.swingExtensions.CustomBoxPanel;
import presentationLayer.swingExtensions.CustomButton;
import presentationLayer.swingExtensions.CustomPanel;
import presentationLayer.swingExtensions.GridConstraintsSpec;

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
	private JPanel groceryItemPanel;
	private boolean buttonPanelFlag;

	public CompressedListView(ItemManager inv) {
		this.inv = inv;

		this.setLayout(new GridBagLayout());
		this.setBackground(Color.black);
		this.displayItems = inv.getItems();
		
		GridBagConstraints c = new GridBagConstraints();

		list = new JList<String>();
		list.setBackground(new Color(185, 185, 185));
		list.setFont(new Font("Arial", Font.BOLD, 18));

		scroll = new JScrollPane(list);
		scroll.setBorder(BorderFactory.createLineBorder(Color.white));

		JPanel viewPanel = new CustomPanel(Color.black, new BorderLayout(), 5);
		viewPanel.add(scroll);
		c = GridConstraintsSpec.stretchableFillConstraints(0, 0, 1, 1, GridBagConstraints.BOTH);
		this.add(viewPanel, c);

		buttonPanel = new CustomPanel(Color.black, new BorderLayout(), 5);
		c = GridConstraintsSpec.stretchableFillConstraints(0, 1, 1, 0, GridBagConstraints.HORIZONTAL);
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

		groceryItemPanel = new CustomPanel(Color.black, new BorderLayout());
		buttonPanel.add(groceryItemPanel , BorderLayout.LINE_START);
		
		groceryListButton = new CustomButton("Add to Grocery List", this, 10);
		groceryItemPanel.add(groceryListButton);

		this.buttonPanelFlag = true;
		this.generateList(displayItems);
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

			this.displayItems.set(itemIndex, item);
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

			this.displayItems.set(itemIndex, item);
			this.stringItemList.set(itemIndex, this.displayItems.get(itemIndex).getDescription());
			this.list.revalidate();
			
			int itemStockPercent = item.getStockableItem().calculatePercent();
			int groceryThreshold = App.getInstance().getSettings().getAddGroceryListThreshold();
			
			if ( (itemStockPercent < groceryThreshold) && (App.getInstance().getGroceryList().itemIndex(item) == -1) ) {
				groceryView.visualAdd(item);
			}
		} else if (e.getSource() == remButton) {
			int itemIndex = list.getSelectedIndex();
			int confirm = JOptionPane.showConfirmDialog(AppWindow.getWindow(), 
	                "Are you sure you want to remove " + displayItems.get(itemIndex).getFoodItem().getName(), 
	                "Item Removal Confirmation", JOptionPane.YES_NO_OPTION);
			
			if (confirm == 0) {
				this.inv.remove(this.displayItems.get(itemIndex));
				this.displayItems.remove(itemIndex);
				this.stringItemList.remove(itemIndex);
			}
		} else if (e.getSource() == groceryListButton) {
			int itemIndex = list.getSelectedIndex();
			groceryView.visualAdd(this.displayItems.get(itemIndex));
		}
	}
	
	public void setGrocery(GroceryListView grocery) {
		this.groceryView = grocery;
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

	@Override
	public void setStockChangeMode(boolean incrementEnabled, boolean decrementEnabled) {
		incButton.setEnabled(incrementEnabled);
		decButton.setEnabled(decrementEnabled);
	}

	@Override
	public void removeGroceryLink() {
		this.groceryItemPanel.remove(groceryListButton);
		this.repaint();
		this.revalidate();
	}
}
