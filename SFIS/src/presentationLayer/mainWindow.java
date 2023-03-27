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
import domainLayer.AlphabeticalSorting;
import domainLayer.DBProxy;
import domainLayer.DepletedSorting;
import domainLayer.Fridge;
import domainLayer.FridgeItem;
import domainLayer.ISortingStrategy;
import domainLayer.GroceryList;
import domainLayer.StoredItem;
import domainLayer.UnalphabeticalSorting;
import domainLayer.FoodItem.StockType;
import presentationLayer.swingExtensions.CustomBoxPanel;
import presentationLayer.swingExtensions.CustomButton;
import presentationLayer.swingExtensions.CustomPanel;
import presentationLayer.swingExtensions.GridConstraintsSpec;
import presentationLayer.swingExtensions.LabelledInputField;

public class mainWindow extends JPanel implements ActionListener{	
	// Input Components
	private JTextField search;
	private JButton addButton, searchButton, favoritesButton;
	private JComboBox<String> sortMethodType;
	private Map<String, ISortingStrategy> sortMethodMap;
	
	// Domain-logic
	private Fridge inv;
	private GroceryList groc;
	// Components required to manage the view for item list
	private JPanel viewPanel;
	private JButton viewToggler;
	private ListViewManager viewManager;
	
	// View Toggle Icons
	private ImageIcon compressedIcon;
	private ImageIcon expressiveIcon;
	
	
	public mainWindow() {
	    // create our jframe as usual
		inv = App.getInstance().getInventory();
		groc = App.getInstance().getGroceryList();
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
	    c = GridConstraintsSpec.stretchableFillConstraints(1, 1, 0.05, 0, GridBagConstraints.HORIZONTAL);
	    searchPanel.add(midPanel, c);
	    
	    // Grocery List Panel
	    JPanel rightPanel = new CustomPanel(Color.BLACK, new BorderLayout(), 5);
	    rightPanel.add(groceryView);
	    c = GridConstraintsSpec.stretchableFillConstraints(2, 1, 0.33, 1, GridBagConstraints.BOTH);
	    searchPanel.add(rightPanel, c);
	}
	
	public void headerSetup() {
		JPanel panel = new CustomPanel(Color.black, null);
		panel.setBorder(BorderFactory.createEmptyBorder(25,40,40,40));
		this.add(panel,BorderLayout.NORTH);
	    
	    JLabel titleLabel = new JLabel("Smart Fridge Tracker");
	    titleLabel.setForeground(Color.white);
	    titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
	    panel.add(titleLabel);
	}
	
	
	public void topLayerSetup(JPanel parent) {
		// START: Item Search Panel
		GridBagConstraints c = new GridBagConstraints();
		
	    JPanel topPanel = new CustomPanel(Color.black, new GridBagLayout(), 10);
	    c = GridConstraintsSpec.stretchableFillConstraints(0, 0, 0.62, 0, GridBagConstraints.HORIZONTAL);
	    parent.add(topPanel, c);
	    
	    LabelledInputField searchInputPanel = new LabelledInputField(Color.black, Color.white, "Search Item:", 16, 16, 5);
	    searchInputPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	    c = GridConstraintsSpec.stretchableFillConstraints(0, 0, 1, 0, GridBagConstraints.BOTH);
	    topPanel.add(searchInputPanel, c);
	    
	    this.search = searchInputPanel.getTextField();
	    
	    JPanel buttonWrapper = new CustomPanel(Color.black, null);
	    searchButton = new CustomButton("Search", this, 10);
	    buttonWrapper.add(searchButton);
	    c = GridConstraintsSpec.coordinateConstraints(1, 0);
	    topPanel.add(buttonWrapper, c);
	    
	    addButton = new CustomButton("Add Item", this, 10);
	    c = GridConstraintsSpec.coordinateConstraints(2, 0);
	    topPanel.add(addButton, c);
	    
	    
	    JPanel midPanel = new CustomPanel(Color.black, null);
	    c = GridConstraintsSpec.stretchableFillConstraints(1, 0, 0.05, 0, GridBagConstraints.HORIZONTAL);
	    parent.add(midPanel, c);
	    
	    JPanel topPanel2 = new CustomPanel(Color.black, new BorderLayout(), 10);
	    c = GridConstraintsSpec.stretchableFillConstraints(2, 0, 0.33, 0, GridBagConstraints.HORIZONTAL);
	    parent.add(topPanel2, c);
	    
	    favoritesButton = new CustomButton("Favorites List", this, 10);
	    topPanel2.add(favoritesButton, BorderLayout.LINE_START);
	    // END: Search Panel
	    
	    // Set up View Toggler Button
	    compressedIcon = new ImageIcon("resources/CompressedViewIcon.png");
	    compressedIcon = new ImageIcon(compressedIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
	    expressiveIcon = new ImageIcon("resources/ExpressiveViewIcon.png");
	    expressiveIcon = new ImageIcon(expressiveIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
	    viewToggler = new CustomButton(null, this, 3);
	    viewToggler.setIcon(expressiveIcon);
	    topPanel2.add(viewToggler, BorderLayout.LINE_END);
	}
	
	
	public void viewLayerSetup(JPanel parent, GroceryListView groceryView) {
		// Set up the Item List View Panel
		GridBagConstraints c = new GridBagConstraints();
				
	    List<ListView> views = new ArrayList<ListView>();
	    views.add(new CompressedListView(inv, groceryView));
	    views.add(new ExpressiveListView(inv, true, groceryView));
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
	    
	    JPanel sortPanel = new CustomBoxPanel(Color.black, BoxLayout.X_AXIS);
	    labelPanel.add(sortPanel, BorderLayout.LINE_END);
	    
	    JLabel sortLabel = new JLabel("Sort By: ");
	    sortLabel.setForeground(Color.white);
	    sortLabel.setFont(new Font("Arial", Font.BOLD, 16));
	    sortPanel.add(sortLabel);
	    
	    sortPanel.add(Box.createRigidArea(new Dimension(10, 10)));
	    
	    String[] values = {"Alphabetial", "Reverse Alphabetical", "Depletion"};
	    sortMethodMap = new HashMap<>();
	    sortMethodMap.put(values[0], new AlphabeticalSorting());
	    sortMethodMap.put(values[1], new UnalphabeticalSorting());
	    sortMethodMap.put(values[2], new DepletedSorting());
	    sortMethodType = new JComboBox<String>(values);
	    sortMethodType.addActionListener(this);
	    sortPanel.add(sortMethodType);
	    c = GridConstraintsSpec.stretchableFillConstraints(0, 1, 1, 1, GridBagConstraints.BOTH);
	    viewPanel.add((JPanel) viewManager.getCurrentView(), c);
	    
	    c = GridConstraintsSpec.stretchableFillConstraints(0, 1, 0.62, 1, GridBagConstraints.BOTH);
	    parent.add(viewPanel, c);
	    // END: Search and Item List Panel
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == addButton) {
			JPanel addView = new addWindow(this);
			AppWindow.getWindow().loadNewView(addView);
		}
		else if (e.getSource() == favoritesButton) {
			JPanel favoritesView = new FavoritesView(this);
			AppWindow.getWindow().loadNewView(favoritesView);
		}
		else if (e.getSource() == searchButton) {		
			mainSearchHandler();
		}
		else if (e.getSource() == viewToggler) {
			this.mainViewToggleHandler();
		}
		else if (e.getSource() == this.sortMethodType) {
			ISortingStrategy strat = this.sortMethodMap.get((String) sortMethodType.getSelectedItem());
			App.getInstance().getInventory().setSortingStrategy(strat);
			App.getInstance().getInventory().sort();
			this.reloadLists();
		}
	}
	
	public void mainViewToggleHandler() {
		if (viewToggler.getIcon() == compressedIcon) {
			viewToggler.setIcon(expressiveIcon);
		} else {
			viewToggler.setIcon(compressedIcon);
		}
		
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
		//We want to ensure we can search our JList, and let it return to its former state if the search is cleared.
		//We do this by passing our inventory and using .contains to add eveyrthing that matches to our JList 
		
		String searchString = search.getText();
		List<StoredItem> matchingItems = inv.search(searchString);
		viewManager.setViewLists(matchingItems);
	}
	
	public void addNewItem() {
		int itemIndex = inv.getItems().size() - 1;
		viewManager.addItemToLists(inv.getItems().get(itemIndex));
	}
	
	
	public void reloadLists() {
		viewManager.setViewLists(App.getInstance().getInventory().getItems());
	}
}
