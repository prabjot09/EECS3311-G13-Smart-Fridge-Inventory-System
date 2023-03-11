package presentationLayer;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import appLayer.App;
import domainLayer.Export;
import domainLayer.ItemManager;
import domainLayer.StoredItem;
import presentationLayer.swingExtensions.CustomPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GroceryListView extends JPanel implements ActionListener {
	
	//variables
	private JButton removeGroceryButton, exportButton;
	private JComboBox<String> exportDecision;
	private ItemManager groceryInv;
	private JList<String> viewList;
	private List<StoredItem> items;
	private DefaultListModel<String> viewListItems;
	
	public GroceryListView(ItemManager inv) {
		this.groceryInv = inv;
	    //this.setBounds(0, 0, 500, 500);
	    this.setBackground(Color.BLACK);
	    this.setLayout(new GridBagLayout());
	    
	    GridBagConstraints c = new GridBagConstraints();
	    
	    JPanel topPanel = new CustomPanel(Color.black, new BorderLayout(), 5);
	    c.gridx = 0;
	    c.gridy = 0;
	    c.weightx = 1;
	    c.fill = GridBagConstraints.HORIZONTAL;
	    this.add(topPanel, c);
	    
	    //Panel for the title 
	    JPanel titlePanel = new CustomPanel(Color.black, 0);
	    topPanel.add(titlePanel, BorderLayout.LINE_START);
	    //Title 
	    JLabel groceryTitle = new JLabel("Your Grocery List");
	    groceryTitle.setForeground(Color.white);
	    groceryTitle.setFont(new Font("Arial", Font.BOLD, 16));
	    titlePanel.add(groceryTitle);
	    
	    String exportChoices[] = {"Grocery List", "Favorites List"};
	    exportDecision = new JComboBox<String>(exportChoices);
	    //exportDecision.setPreferredSize(new Dimension(110,20));
	    topPanel.add(exportDecision, BorderLayout.LINE_END);
	    
	    //Grocery viewing  
	    viewList = new JList<String>();
	    viewList.setBackground(Color.GRAY);
	    viewList.setFont(new Font("Arial", Font.BOLD, 18));
	    JScrollPane scrollViewingPane = new JScrollPane(viewList);
	    scrollViewingPane.setBorder(BorderFactory.createLineBorder(Color.WHITE));
	    //scrollViewingPane.setPreferredSize(new Dimension(400,280));
	    
	    JPanel viewWrapper = new CustomPanel(Color.black, new BorderLayout(), 5);
	    viewWrapper.add(scrollViewingPane);
	    
	    c.gridx = 0;
	    c.gridy = 1;
	    c.weightx = 1;
	    c.weighty = 1;
	    c.fill = GridBagConstraints.BOTH;
	    this.add(viewWrapper, c);
	    
	    //Panel for buttons
	    JPanel buttonsPanel = new CustomPanel(Color.black, new BorderLayout(), 5);
	    c.gridx = 0;
	    c.gridy = 2;
	    c.weightx = 1;
	    c.weighty = 0;
	    c.fill = GridBagConstraints.HORIZONTAL;
	    this.add(buttonsPanel, c);
	    //Remove grocery button
	    removeGroceryButton = new JButton("Remove Grocery Item");
	    removeGroceryButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    removeGroceryButton.addActionListener(this);
	    buttonsPanel.add(removeGroceryButton, BorderLayout.LINE_END);
	    //Export choices combo box
	    
	    //Export Button
	    exportButton = new JButton("Export");
	    exportButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    exportButton.addActionListener(this);
	    buttonsPanel.add(exportButton, BorderLayout.LINE_START);
	    
	    //Grocery dynamic view
	    items = this.groceryInv.getItems();
	    viewListItems = new DefaultListModel<String>();
	    for (StoredItem item : items) {
	    	viewListItems.addElement("- " + item.getFoodItem().getName());
	    }
	    viewList.setModel(viewListItems);
	    revalidate();
	    
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//Remove button functionality 
		if (e.getSource() == removeGroceryButton) {
			if (viewList.getSelectedIndex() == -1) {
				JOptionPane.showMessageDialog(null, "Please select an item in the grocery list", "Notice", JOptionPane.WARNING_MESSAGE);
				return;
			}
			items = groceryInv.getItems();
			int deleteIndex = viewList.getSelectedIndex();
			groceryInv.remove(items.get(deleteIndex));
			viewListItems.remove(deleteIndex);
			revalidate();
		}
		//Export button functionality 
		if (e.getSource() == exportButton) {
			if (exportDecision.getSelectedItem() == "Grocery List") {
				new Export(groceryInv.getItems());
			}
			if (exportDecision.getSelectedItem() == "Favorites List") {
				new Export(App.getInstance().getFavorites().getItems());
			}
		}
	}
	public void visualAdd(StoredItem item) {
		if (groceryInv.itemIndex(item) != -1) {
			viewListItems.addElement("- " + item.getFoodItem().getName());
			revalidate();
		}
		else {
			JOptionPane.showMessageDialog(null, "Item is not in grocery list and therefore cannot be displayed", "Notice", JOptionPane.WARNING_MESSAGE);
		}
	}

}
