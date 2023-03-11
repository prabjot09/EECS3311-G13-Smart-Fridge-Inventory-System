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

public class mainWindow implements ActionListener{	
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
	
	//frame
	private JFrame jframe;
	
	
	public mainWindow() {
	    // create our jframe as usual
		inv = App.getInstance().getInventory();
		groc = App.getInstance().getGroceryList();
		jframe = new JFrame("SFIS");
		jframe.getContentPane().setLayout(new BorderLayout());
		GroceryListView groceryView = new GroceryListView(groc);
		
		headerSetup();	 
	    
	    // START: Search and Item List Panel
	    JPanel searchPanel = new CustomPanel(Color.black, new GridBagLayout(), 20);
	    //searchPanel.setBounds(0,100,1000,500);
	    jframe.add(searchPanel);
	    
	    
	    topLayerSetup(searchPanel);
	    
	    viewLayerSetup(searchPanel, groceryView);
	    
	    GridBagConstraints c = new GridBagConstraints();
	    
	    JPanel midPanel = new CustomPanel(Color.black, null);
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.gridx = 1;
	    c.gridy = 1;
	    c.weightx = 0.05;
	    searchPanel.add(midPanel, c);
	    
	    c.fill = GridBagConstraints.BOTH;
	    c.gridx = 2;
	    c.gridy = 1;
	    c.weightx = 0.33;
	    c.weighty = 1;
	    // Grocery List Panel
	    JPanel rightPanel = new CustomPanel(Color.BLACK, new BorderLayout(), 5);
	    //rightPanel.setBorder(BorderFactory.createLineBorder(Color.red));
	    //rightPanel.setPreferredSize(new Dimension(400, 400));
	    rightPanel.add(groceryView);
	    //groceryView.setBorder(BorderFactory.createLineBorder(Color.blue));
	    searchPanel.add(rightPanel, c);
	    
	    // Update DB when closing the window
	    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    jframe.addWindowListener(new WindowAdapter() {
	    	@Override
	    	public void windowClosing(WindowEvent e) {
	    		DBProxy.getInstance().updateFridge(mainWindow.this.inv);
	    		DBProxy.getInstance().updateGroceryItems(groc);
	    	}
	    });
	    
	    // set the jframe size and location, and make it visible
	    jframe.getContentPane().setBackground(Color.black);
	    jframe.setPreferredSize(new Dimension(1140, 650));
	    jframe.pack();
	    jframe.setLocationRelativeTo(null);
	    jframe.setVisible(true);
		
	}
	
	public void headerSetup() {
		JPanel panel = new CustomPanel(Color.black, null);
		panel.setBorder(BorderFactory.createEmptyBorder(25,40,40,40));
		jframe.add(panel,BorderLayout.NORTH);
	    
	    JLabel titleLabel = new JLabel("Smart Fridge Tracker");
	    titleLabel.setForeground(Color.white);
	    titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
	    panel.add(titleLabel);
	}
	
	
	public void topLayerSetup(JPanel parent) {
		// START: Item Search Panel
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.62;
		c.fill = GridBagConstraints.HORIZONTAL;
		
	    JPanel topPanel = new CustomPanel(Color.black, new GridBagLayout(), 10);
	    parent.add(topPanel, c);
	    
	    JLabel searchLabel = new JLabel("Search Item:  ");
	    searchLabel.setFont(new Font("Arial", Font.BOLD, 16));
	    searchLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	    searchLabel.setForeground(Color.white);
	    c.weightx = 0;
	    c.fill = GridBagConstraints.NONE;
	    topPanel.add(searchLabel, c);
	    
	    JPanel searchFieldWrapper = new CustomPanel(Color.black, new BorderLayout());
	    search = new JTextField();
	    search.setFont(new Font("Arial", Font.PLAIN, 16));
	    search.setBackground(Color.gray);
	    searchFieldWrapper.add(search);
	    //search.setBounds(0,100,300,500);
	    search.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    //search.setPreferredSize(new Dimension(300,50));
	    c.gridx = 1;
	    c.gridy = 0;
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.weightx = 1;
	    topPanel.add(searchFieldWrapper, c);
	    
	    JPanel buttonWrapper = new CustomPanel(Color.black, null);
	    searchButton = new CustomButton("Search", this, 10);
	    buttonWrapper.add(searchButton);
	    c.gridx = 2;
	    c.gridy = 0;
	    c.weightx = 0;
	    topPanel.add(buttonWrapper, c);
	    
	    addButton = new CustomButton("Add Item", this, 10);
	    c.gridx = 3;
	    c.gridy = 0;
	    topPanel.add(addButton, c);
	    
	    
	    JPanel midPanel = new CustomPanel(Color.black, null);
	    c.gridx = 1;
	    c.gridy = 0;
	    c.weightx = 0.05;
	    c.fill = GridBagConstraints.HORIZONTAL;
	    parent.add(midPanel, c);
	    
	    JPanel topPanel2 = new CustomPanel(Color.black, new BorderLayout(), 10);
	    c.gridx = 2;
	    c.gridy = 0;
	    c.weightx = 0.33;
	    c.fill = GridBagConstraints.HORIZONTAL;
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
	    //viewToggler.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
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
	    c.gridx = 0;
	    c.gridy = 0;
	    c.weightx = 1;
	    c.fill = GridBagConstraints.HORIZONTAL;
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
	    //sortLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	    sortPanel.add(sortLabel);
	    
	    sortPanel.add(Box.createRigidArea(new Dimension(10, 10)));
	    
	    String[] values = {"Alphabetial", "Reverse Alphabetical", "Depletion"};
	    sortMethodMap = new HashMap<>();
	    sortMethodMap.put(values[0], new AlphabeticalSorting());
	    sortMethodMap.put(values[1], new UnalphabeticalSorting());
	    sortMethodMap.put(values[2], new DepletedSorting());
	    sortMethodType = new JComboBox<String>(values);
	    //sortMethodType.setFont(new Font("Arial", Font.PLAIN, 14));
	    //sortMethodType.setPreferredSize(new Dimension(300,50));
	    sortMethodType.addActionListener(this);
	    sortPanel.add(sortMethodType);
	    
	    c.gridx = 0;
	    c.gridy = 1;
	    c.weightx = 1;
	    c.weighty = 1;
	    c.fill = GridBagConstraints.BOTH;
	    //((JPanel) viewManager.getCurrentView()).setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	    viewPanel.add((JPanel) viewManager.getCurrentView(), c);
	    
	    c.gridx = 0;
	    c.gridy = 1;
	    c.weighty = 1;
	    c.weightx = 0.62;
	    c.fill = GridBagConstraints.BOTH;
	    parent.add(viewPanel, c);
	    // END: Search and Item List Panel
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == addButton) {
			new addWindow(this);
			jframe.setVisible(false);
		}
		else if (e.getSource() == favoritesButton) {
			new FavoritesView(this);
			jframe.setVisible(false);
		}
		else if (e.getSource() == searchButton) {		
			mainSearchHandler();
		}
		else if (e.getSource() == viewToggler) {
			this.mainViewToggleHandler();
		}
		else if (e.getSource() == this.sortMethodType) {
			ISortingStrategy strat = this.sortMethodMap.get((String) sortMethodType.getSelectedItem());
			List<StoredItem> sortedList = strat.sortItems(App.getInstance().getInventory().getItems());
			App.getInstance().getInventory().setItems(sortedList);
			viewManager.setViewLists(App.getInstance().getInventory().getItems());
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
		c.gridx = 0;
	    c.gridy = 1;
	    c.weighty = 1;
	    c.fill = GridBagConstraints.BOTH;
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
	
	//makes frame visible
	public void makeVisible() {
		jframe.setVisible(true);
	}
	
	public void reloadLists() {
		viewManager.setViewLists(App.getInstance().getInventory().getItems());
	}
}
