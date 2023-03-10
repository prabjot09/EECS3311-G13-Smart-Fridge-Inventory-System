package presentationLayer;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import appLayer.App;
import domainLayer.Export;
import domainLayer.ItemManager;
import domainLayer.StoredItem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
	    this.setBounds(0, 0, 500, 500);
	    this.setBackground(Color.WHITE);
	    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	    
	    //Panel for the title 
	    JPanel titlePanel = new JPanel();
	    titlePanel.setBackground(Color.BLACK);
	    titlePanel.setBorder(BorderFactory.createEmptyBorder(10,10,17,10));
	    this.add(titlePanel);
	    //Title 
	    JLabel groceryTitle = new JLabel("Your Grocery List");
	    groceryTitle.setForeground(Color.white);
	    groceryTitle.setFont(new Font("Arial", Font.PLAIN, 26));
	    titlePanel.add(groceryTitle);
	    
	    //Grocery viewing 
	    viewList = new JList<String>();
	    viewList.setBackground(Color.GRAY);
	    JScrollPane scrollViewingPane = new JScrollPane(viewList);
	    scrollViewingPane.setBorder(BorderFactory.createLineBorder(Color.WHITE));
	    scrollViewingPane.setPreferredSize(new Dimension(400,280));
	    this.add(scrollViewingPane);
	    
	    //Panel for buttons
	    JPanel buttonsPanel = new JPanel();
	    buttonsPanel.setBackground(Color.BLACK);
	    buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	    buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
	    this.add(buttonsPanel);
	    //Remove grocery button
	    removeGroceryButton = new JButton("Remove Grocery");
	    removeGroceryButton.setPreferredSize(new Dimension(135,20));
	    removeGroceryButton.addActionListener(this);
	    buttonsPanel.add(removeGroceryButton);
	    //Export choices combo box
	    String exportChoices[] = {"Grocery List", "Favorites List"};
	    exportDecision = new JComboBox<String>(exportChoices);
	    exportDecision.setPreferredSize(new Dimension(110,20));
	    buttonsPanel.add(exportDecision);
	    //Export Button
	    exportButton = new JButton("Export");
	    exportButton.setPreferredSize(new Dimension(75,20));
	    exportButton.addActionListener(this);
	    buttonsPanel.add(exportButton);
	    
	    //Grocery dynamic view
	    items = this.groceryInv.getItems();
	    viewListItems = new DefaultListModel<String>();
	    for (StoredItem item : items) {
	    	viewListItems.addElement("- "+item.getFoodItem().getName());
	    }
	    viewList.setModel(viewListItems);
	    revalidate();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == removeGroceryButton) {
			int deleteIndex = viewList.getSelectedIndex();
			groceryInv.remove(items.get(deleteIndex));
			viewListItems.remove(deleteIndex);
			revalidate();
		}
		if (e.getSource() == exportButton) {
			if (exportDecision.getSelectedItem() == "Grocery List") {
				new Export(groceryInv.getItems());
			}
			if (exportDecision.getSelectedItem() == "Favorites List") {
				new Export(App.getInstance().getFavorites().getItems());
			}
		}
	}

}
