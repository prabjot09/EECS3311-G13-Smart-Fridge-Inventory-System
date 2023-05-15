package presentationLayer;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import appLayer.App;
import domainLayer.Export;
import domainLayer.GroceryList;
import domainLayer.ItemManager;
import domainLayer.StoredItem;
import presentationLayer.swingExtensions.CustomButton;
import presentationLayer.swingExtensions.CustomPanel;
import presentationLayer.swingExtensions.GridConstraintsSpec;

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
	private GroceryList groceryInv;
	private JList<String> viewList;
	private List<StoredItem> items;
	private DefaultListModel<String> viewListItems;
	
	public GroceryListView(GroceryList inv) {
		this.groceryInv = inv;
	    //this.setBounds(0, 0, 500, 500);
	    this.setBackground(Color.BLACK);
	    this.setLayout(new GridBagLayout());
	    
	    GridBagConstraints c = new GridBagConstraints();
	    
	    JPanel topPanel = new CustomPanel(Color.black, new BorderLayout(), 5);
	    c = GridConstraintsSpec.stretchableFillConstraints(0, 0, 1, 0, GridBagConstraints.HORIZONTAL);
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
	    viewList.setBackground(new Color(185, 185, 185));
	    viewList.setFont(new Font("Arial", Font.BOLD, 18));
	    JScrollPane scrollViewingPane = new JScrollPane(viewList);
	    scrollViewingPane.setBorder(BorderFactory.createLineBorder(Color.WHITE));
	    //scrollViewingPane.setPreferredSize(new Dimension(400,280));
	    
	    JPanel viewWrapper = new CustomPanel(Color.black, new BorderLayout(), 5);
	    viewWrapper.add(scrollViewingPane);
	    
	    c = GridConstraintsSpec.stretchableFillConstraints(0, 1, 1, 1, GridBagConstraints.BOTH);
	    this.add(viewWrapper, c);
	    
	    //Panel for buttons
	    JPanel buttonsPanel = new CustomPanel(Color.black, new BorderLayout(), 5);
	    c = GridConstraintsSpec.stretchableFillConstraints(0, 2, 1, 0, GridBagConstraints.HORIZONTAL);
	    this.add(buttonsPanel, c);
	    //Remove grocery button
	    removeGroceryButton = new CustomButton("Remove Grocery Item", this);
	    removeGroceryButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
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
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Grocery List Export Destination");
		    fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    fc.setAcceptAllFileFilterUsed(false);
		    
			int result = fc.showOpenDialog(AppWindow.getWindow());
			
			if (result != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null, "File Not Exported.\n" + "Please select a valid file destination!", "Notice", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			if (exportDecision.getSelectedItem() == "Grocery List") {
				new Export(groceryInv.getItems(), fc.getSelectedFile());
			}
			if (exportDecision.getSelectedItem() == "Favorites List") {
				new Export(App.getInstance().getFavorites().getItems(), fc.getSelectedFile());
			}
		}
	}
	public void visualAdd(StoredItem item) {
		try {
			groceryInv.add(item);
			viewListItems.clear();
			items = this.groceryInv.getItems();
		    for (StoredItem storedItem : items) {
		    	viewListItems.addElement("- " + storedItem.getFoodItem().getName());
		    }
			revalidate();
		}
		catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Item already exists within the grocery list", "Notice", JOptionPane.WARNING_MESSAGE);
		}	
	}
	
}
