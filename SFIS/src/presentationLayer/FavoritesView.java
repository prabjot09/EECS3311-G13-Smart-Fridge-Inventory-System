package presentationLayer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import appLayer.App;
import domainLayer.DBProxy;
import domainLayer.FavoritesList;
import domainLayer.FoodItem.StockType;
import domainLayer.Pair;
import domainLayer.StockableItem;
import domainLayer.StockableItemFactory;
import domainLayer.StoredItem;

public class FavoritesView extends JFrame implements ActionListener, ListSelectionListener{
	private CompressedListView fridgeItems;
	private ExpressiveListView favoritesView;
	private mainWindow homeView;
	
	private JButton backButton;
	private JButton addButton;
	
	private JPanel inputPanel;
	private JPanel quantityPanel;
	private StockInputField quantityInput;
	
	public FavoritesView(mainWindow homeView) {	
		this.homeView = homeView;
		
		BoxLayout overallLayout = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);
		this.getContentPane().setLayout(overallLayout);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		titlePanel.setBackground(Color.black);
		titlePanel.setMaximumSize(new Dimension(titlePanel.getMaximumSize().width, titlePanel.getPreferredSize().height));
	    this.add(titlePanel);
	    
	    JLabel titleLabel = new JLabel("Favorited Items");
	    titleLabel.setForeground(Color.white);
	    titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
	    titlePanel.add(titleLabel);
	    
	    JPanel backPanel = new JPanel();
	    backPanel.setBackground(Color.black);
	    backPanel.setBorder(BorderFactory.createEmptyBorder(0,20,0,20));
	    backPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
	    this.add(backPanel);
	    backButton = new JButton("Back");
	    backButton.addActionListener(this);
	    backPanel.add(backButton);
	    
	    JPanel viewPanel = new JPanel();
	    viewPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	    viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.X_AXIS));
		viewPanel.setBackground(Color.black);
	    this.add(viewPanel);
	    
	    JPanel fridgePanel = new JPanel();
	    fridgePanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	    fridgePanel.setLayout(new BoxLayout(fridgePanel, BoxLayout.Y_AXIS));
	    fridgePanel.setBackground(Color.black);
	    viewPanel.add(fridgePanel);
	    
	    JPanel labelWrap = new JPanel();
	    labelWrap.setBackground(Color.black);
	    labelWrap.setLayout(new FlowLayout(FlowLayout.LEFT));
	    fridgePanel.add(labelWrap);
	    
	    JLabel fridgeLabel = new JLabel("Your Fridge Items");
	    fridgeLabel.setForeground(Color.white);
	    fridgeLabel.setFont(new Font("Arial", Font.BOLD, 16));
	    labelWrap.add(fridgeLabel);
	    
	    fridgeItems = new CompressedListView(App.getInstance().getInventory());
	    fridgeItems.setButtonPanelFlag(false);
	    fridgeItems.setListListener(this);
	    fridgePanel.add(fridgeItems);
	    
	    JPanel favoritesPanel = new JPanel();
	    favoritesPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	    favoritesPanel.setLayout(new BoxLayout(favoritesPanel, BoxLayout.Y_AXIS));
	    favoritesPanel.setBackground(Color.black);
	    viewPanel.add(favoritesPanel);
	    
	    labelWrap = new JPanel();
	    labelWrap.setBackground(Color.black);
	    labelWrap.setLayout(new FlowLayout(FlowLayout.LEFT));
	    favoritesPanel.add(labelWrap);
	    
	    JLabel favoritesLabel = new JLabel("Your Favorites List");
	    favoritesLabel.setForeground(Color.white);
	    favoritesLabel.setFont(new Font("Arial", Font.BOLD, 16));
	    labelWrap.add(favoritesLabel);
	    
	    favoritesView = new ExpressiveListView(App.getInstance().getFavorites());	  
	    favoritesPanel.add(favoritesView);
	    
	    inputPanel = new JPanel();
	    inputPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		inputPanel.setBackground(Color.black);
	    this.add(inputPanel);
	    
	    quantityPanel = new JPanel();
	    quantityPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	    quantityPanel.setBackground(Color.black);
	    
	    JLabel quantityLabel = new JLabel("Item Quantity: ");
	    quantityLabel.setForeground(Color.white);
	    quantityLabel.setFont(new Font("Arial", Font.BOLD, 20));
	    quantityPanel.add(quantityLabel);
	    
	    quantityInput = new StockInputField();
	    
	    addButton = new JButton("Add to Favorites List");
	    addButton.addActionListener(this);
	    addButton.setEnabled(false);
	    inputPanel.add(addButton);
	    
	    //dispose on close while also opening mainwindow on close
	    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    this.addWindowListener(new WindowAdapter() {
	    	@Override
	    	public void windowClosing(WindowEvent e) {
	    		DBProxy.getInstance().updateFavoritedItems(App.getInstance().getFavorites());
	    		homeView.makeVisible();
	    	}
	    });
	    this.getContentPane().setBackground(Color.black);
	    // set the jframe size and location, and make it visible
	    this.setPreferredSize(new Dimension(1000, 600));
	    this.pack();
	    this.setLocationRelativeTo(null);
	    this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backButton) {
			homeView.makeVisible();
			DBProxy.getInstance().updateFavoritedItems(App.getInstance().getFavorites());
			this.dispose();
			return;
		}
		else if (e.getSource() == addButton) {
			addHandler();
		}		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		StoredItem selectedItem = fridgeItems.getSelectedItem();
		if (selectedItem == null) {
			inputPanel.remove(quantityPanel);
			addButton.setEnabled(false);
			inputPanel.revalidate();
			return;
		}
		
		if (quantityPanel.getComponentCount() == 2) {
			quantityPanel.remove(1);
		}
		
		StockType type = selectedItem.getFoodItem().getStockType();
		quantityPanel.add(quantityInput.getAmountField(type.toString()));
		
		inputPanel.remove(addButton);
		if (inputPanel.getComponentCount() == 0) {
			inputPanel.add(quantityPanel);
		}
		inputPanel.add(addButton);
		addButton.setEnabled(true);
		inputPanel.revalidate();
	}
	
	private void addHandler() {
		StoredItem selectedItem = fridgeItems.getSelectedItem();
		if (selectedItem == null) {
			JOptionPane.showMessageDialog(this, "No item has been selected.", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		StockType type = selectedItem.getFoodItem().getStockType();
		Pair<StockType, Integer> input = quantityInput.getAmountValue(type.toString());
		
		if (input.getB() == null) {
			JOptionPane.showMessageDialog(this, "Valid input quantity must be selected.", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		StoredItem newItem = selectedItem.copy();
		StockableItem stock = StockableItemFactory.createStockableItem(input.getA(), input.getB());
		newItem.setStockableItem(stock);
		
		try {
			FavoritesList favorites = App.getInstance().getFavorites();
			favorites.add(newItem);
			
			favoritesView.generateList(favorites.getItems());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
}
