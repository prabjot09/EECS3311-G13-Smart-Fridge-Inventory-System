package presentationLayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import appLayer.App;
import domainLayer.DBProxy;
import domainLayer.Fridge;
import domainLayer.FridgeItem;
import domainLayer.GroceryList;
import domainLayer.StoredItem;
import domainLayer.FoodItem.StockType;
import domainLayer.itemSorting.AlphabeticalSorting;
import domainLayer.itemSorting.DepletedSorting;
import domainLayer.itemSorting.ISortingStrategy;
import domainLayer.itemSorting.UnalphabeticalSorting;
import presentationLayer.ListViewManager.StockChangeMode;
import presentationLayer.itemAdditionUI.addWindow;
import presentationLayer.swingExtensions.CustomBoxPanel;
import presentationLayer.swingExtensions.CustomButton;
import presentationLayer.swingExtensions.CustomPanel;
import presentationLayer.swingExtensions.CustomToggleButton;
import presentationLayer.swingExtensions.GridConstraintsSpec;
import presentationLayer.swingExtensions.LabelledInputField;

public class MainFridgeView extends AppFrameView implements ActionListener{	
	// Input Components
	private JTextField search;
	private JButton addButton, searchButton, backButton;
	private JComboBox<String> sortMethodType;
	private Map<String, ISortingStrategy> sortMethodMap;
	
	// Domain-logic
	private Fridge inv;
	private GroceryList groc;
	// Components required to manage the view for item list
	private JPanel viewPanel;
	private CustomToggleButton viewToggler, adjustmentToggler;
	private ListViewManager viewManager;

	private boolean smartAdjustmentState;
	
	
	public MainFridgeView() {
	    // create our jframe as usual
		inv = App.getInstance().getInventory();
		groc = App.getInstance().getGroceryList();
		smartAdjustmentState = App.getInstance().getSettings().isSmartFeaturesEnabled() && 
								!App.getInstance().getHistory().isModifiedToday();
		this.setLayout(new BorderLayout());
		GroceryListView groceryView = new GroceryListView(groc);
		
		headerSetup();	 
	    
	    // START: Search and Item List Panel
	    JPanel searchPanel = new CustomPanel(Color.black, new GridBagLayout(), 20);
	    this.add(searchPanel);
	    
	    
	    topLayerSetup(searchPanel);
	    
	    viewLayerSetup(searchPanel, groceryView);
	    
	    GridBagConstraints c = new GridBagConstraints();
	    
	    JPanel midPanel = new CustomPanel(Color.black, null);
	    c = GridConstraintsSpec.stretchableFillConstraints(1, 2, 0.05, 0, GridBagConstraints.HORIZONTAL);
	    searchPanel.add(midPanel, c);
	    
	    // Grocery List Panel
	    JPanel rightPanel = new CustomPanel(Color.BLACK, new BorderLayout(), 5);
	    rightPanel.add(groceryView);
	    c = GridConstraintsSpec.stretchableFillConstraints(2, 2, 0.33, 1, GridBagConstraints.BOTH);
	    searchPanel.add(rightPanel, c);
	    
	    if (smartAdjustmentState) {
	    	String message = "The smart feature has automatically changed the stock of items in your fridge.\n" +
	    					 "Please make any necessary changes to your fridge to reflect its actual state.\n" + 
	    					 "These changes will also be incorporated to make the smart feature more accurate in the future!";
	    	JOptionPane.showMessageDialog(AppWindow.getWindow(), message, "Notice", JOptionPane.INFORMATION_MESSAGE);
	    	viewManager.setStockChangeMode(false, true);
	    }
	}
	
	public void headerSetup() {
		JPanel panel = new CustomPanel(Color.black, null);
		panel.setBorder(BorderFactory.createEmptyBorder(15,40,0,40));
		this.add(panel,BorderLayout.NORTH);
	    
	    JLabel titleLabel = new JLabel("Smart Fridge Tracker");
	    titleLabel.setForeground(Color.white);
	    titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
	    panel.add(titleLabel);
	}
	
	
	public void topLayerSetup(JPanel parent) {
		// START: Item Search Panel
		GridBagConstraints c = new GridBagConstraints();
		
		JPanel backPanel = new CustomPanel(Color.black, new FlowLayout(FlowLayout.LEFT));
		backPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		c = GridConstraintsSpec.stretchableFillConstraints(0, 0, 1, 0, GridBagConstraints.HORIZONTAL);
		parent.add(backPanel, c);
		
		backButton = new CustomButton("Back to Home Page", this, 10);
		backPanel.add(backButton);
		
	    JPanel topPanel = new CustomPanel(Color.black, new GridBagLayout(), 10);
	    c = GridConstraintsSpec.stretchableFillConstraints(0, 1, 0.62, 0, GridBagConstraints.HORIZONTAL);
	    parent.add(topPanel, c);
	    
	    LabelledInputField searchInputPanel = new LabelledInputField(Color.black, Color.white, "Search Item:", 16, 16, 5);
	    searchInputPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
	    c = GridConstraintsSpec.stretchableFillConstraints(0, 0, 1, 0, GridBagConstraints.BOTH);
	    topPanel.add(searchInputPanel, c);
	    
	    this.search = searchInputPanel.getTextField();
	    
	    JPanel buttonWrapper = new CustomPanel(Color.black, null);
	    searchButton = new CustomButton("Search", this, 10);
	    buttonWrapper.add(searchButton);
	    c = GridConstraintsSpec.coordinateConstraints(1, 0);
	    topPanel.add(buttonWrapper, c);
	    
	    c = GridConstraintsSpec.stretchableFillConstraints(2, 0, 0.05, 0, GridBagConstraints.HORIZONTAL);
	    topPanel.add(new CustomPanel(Color.black, null), c);
	    
	    addButton = new CustomButton("Add Item", this, 10);
	    c = GridConstraintsSpec.coordinateConstraints(3, 0);
	    topPanel.add(addButton, c);
	    
	    
	    JPanel midPanel = new CustomPanel(Color.black, null);
	    c = GridConstraintsSpec.stretchableFillConstraints(1, 1, 0.05, 0, GridBagConstraints.HORIZONTAL);
	    parent.add(midPanel, c);
	    
	    JPanel topPanel2 = new CustomPanel(Color.black, new BorderLayout(), 10);
	    c = GridConstraintsSpec.stretchableFillConstraints(2, 1, 0.33, 0, GridBagConstraints.HORIZONTAL);
	    parent.add(topPanel2, c);
	    
	    boolean smartFeatureOn = App.getInstance().getSettings().isSmartFeaturesEnabled();
	    JLabel smartLabel = new JLabel("Smart Feature: " + (smartFeatureOn ? "On" : "Off"));
	    smartLabel.setForeground(smartFeatureOn ? Color.green : Color.red);
	    smartLabel.setFont(new Font("Arial", Font.BOLD, 14));
	    smartLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
	    topPanel2.add(smartLabel, BorderLayout.LINE_START);
	    // END: Search Panel
	    
	    String label1 = "Adjust Yesterday's Consumption";
	    String label2 = "Save Adjustments";
	    adjustmentToggler = new CustomToggleButton(label1, label2, label2, this, 5);
	    if (smartAdjustmentState) {
		    adjustmentToggler.initToggle(true);
	    	topPanel2.add(adjustmentToggler, BorderLayout.LINE_END);
	    }
	}
	
	
	public void viewLayerSetup(JPanel parent, GroceryListView groceryView) {
		// Set up the Item List View Panel
		GridBagConstraints c = new GridBagConstraints();
				
	    List<ListView> views = new ArrayList<ListView>();
	    views.add(new CompressedListView(inv));
	    views.add(new ExpressiveListView(inv));
	    
	    views.get(0).setGrocery(groceryView);
	    views.get(1).setGrocery(groceryView);
	    
	    viewManager = new ListViewManager(views);
	    viewManager.setSizes(new Dimension(750, 400));
	    viewPanel = new CustomPanel(Color.black, new GridBagLayout(), 5);
	    
	    JPanel labelPanel = new CustomPanel(Color.black, new BorderLayout(), 5);
	    c = GridConstraintsSpec.stretchableFillConstraints(0, 0, 1, 0, GridBagConstraints.HORIZONTAL);
	    viewPanel.add(labelPanel, c);
	    
	    JLabel viewLabel = new JLabel("Your Fridge Items");
	    viewLabel.setForeground(Color.white);
	    viewLabel.setFont(new Font("Arial", Font.BOLD, 16));
	    viewLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	    labelPanel.add(viewLabel, BorderLayout.LINE_START);
	    
	    JPanel functionPanel = new CustomBoxPanel(Color.black, BoxLayout.X_AXIS);
	    labelPanel.add(functionPanel, BorderLayout.LINE_END);
	    
	    JLabel sortLabel = new JLabel("Sort By: ");
	    sortLabel.setForeground(Color.white);
	    sortLabel.setFont(new Font("Arial", Font.BOLD, 16));
	    functionPanel.add(sortLabel);
	    
	    functionPanel.add(Box.createRigidArea(new Dimension(10, 10)));
	    
	    String[] values = {"Alphabetical", "Reverse Alphabetical", "Depletion"};
	    sortMethodMap = new HashMap<>();
	    sortMethodMap.put(values[0], new AlphabeticalSorting());
	    sortMethodMap.put(values[1], new UnalphabeticalSorting());
	    sortMethodMap.put(values[2], new DepletedSorting());
	    sortMethodType = new JComboBox<String>(values);
	    sortMethodType.addActionListener(this);
	    functionPanel.add(sortMethodType);
	    c = GridConstraintsSpec.stretchableFillConstraints(0, 1, 1, 1, GridBagConstraints.BOTH);
	    viewPanel.add((JPanel) viewManager.getCurrentView(), c);
	    
	    functionPanel.add(Box.createRigidArea(new Dimension(10, 10)));

	    viewToggler = new CustomToggleButton("View Layout: Compressed", "View Layout: Expressive", "View Layout: Compressed", this, 10);
	    viewToggler.initToggle(true);
	    functionPanel.add(viewToggler);
	    
	    c = GridConstraintsSpec.stretchableFillConstraints(0, 2, 0.62, 1, GridBagConstraints.BOTH);
	    parent.add(viewPanel, c);
	    // END: Search and Item List Panel
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == addButton) {
			AppFrameView addView = new addWindow(this);
			AppWindow.getWindow().loadNewView(addView);
		}
		else if (e.getSource() == searchButton) {		
			mainSearchHandler();
		}
		else if (e.getSource() == viewToggler) {
			mainViewToggleHandler();
		}
		else if (e.getSource() == adjustmentToggler) {
			smartAdjustmentToggleHandler();
		}
		else if (e.getSource() == backButton) {
			AppWindow.getWindow().loadPreviousWindow();
			saveData();
		}
		else if (e.getSource() == this.sortMethodType) {
			ISortingStrategy strat = this.sortMethodMap.get((String) sortMethodType.getSelectedItem());
			App.getInstance().getInventory().setSortingStrategy(strat);
			App.getInstance().getInventory().sort();
			this.reloadLists();
		}
	}
	
	private void smartAdjustmentToggleHandler() {
		if (adjustmentToggler.isOn()) {
			App.getInstance().getHistory().updateHistory(inv, 1);
			viewManager.setStockChangeMode(true, false);
		} else {
			App.getInstance().getHistory().updateHistory(inv, 0);
			viewManager.setStockChangeMode(false, true);
		}
		
		adjustmentToggler.toggle();
	}

	public void mainViewToggleHandler() {
		viewToggler.toggle();
		
		viewPanel.remove((JPanel) viewManager.getCurrentView());
		viewManager.toggle();
		viewManager.getCurrentView().generateList(inv.getItems());
		
		GridBagConstraints c = new GridBagConstraints();
	    c = GridConstraintsSpec.stretchableFillConstraints(0, 1, 0, 1, GridBagConstraints.BOTH);
		viewPanel.add((JPanel) viewManager.getCurrentView(), c);
		viewPanel.revalidate();
		viewPanel.repaint();
	}
	
	
	public void mainSearchHandler() {
		String searchString = search.getText();
		List<StoredItem> matchingItems = inv.search(searchString);
		viewManager.setViewLists(matchingItems);
	}
	
	
	public void reloadLists() {
		viewManager.setViewLists(App.getInstance().getInventory().getItems());
	}
	
	@Override
	public void saveData() {
		if (smartAdjustmentState && adjustmentToggler.isOn()) {
			App.getInstance().getHistory().updateHistory(inv, 1);
		}
		else {
			App.getInstance().getHistory().updateHistory(inv, 0);
		}


		DBProxy.getInstance().updateFridge(inv);
		DBProxy.getInstance().updateGroceryItems(App.getInstance().getGroceryList());
		DBProxy.getInstance().updateUserHistory(App.getInstance().getHistory());
	}
}
