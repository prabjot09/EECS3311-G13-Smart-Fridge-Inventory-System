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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import appLayer.App;
import domainLayer.DBProxy;
import domainLayer.FavoritesList;
import domainLayer.FoodItem.StockType;
import presentationLayer.swingExtensions.CustomBoxPanel;
import presentationLayer.swingExtensions.CustomButton;
import presentationLayer.swingExtensions.CustomPanel;
import domainLayer.Pair;
import domainLayer.StockableItem;
import domainLayer.StockableItemFactory;
import domainLayer.StoredItem;

public class FavoritesView extends JPanel implements ActionListener, ListSelectionListener{
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
		
		//BoxLayout overallLayout = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);
		//this.getContentPane().setLayout(overallLayout);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		titleBuilder();
	    
	    JPanel viewPanel = new CustomBoxPanel(Color.black, BoxLayout.X_AXIS, 10);
	    this.add(viewPanel);
	    
	    fridgeViewBuilder(viewPanel);
	    
	    favoritesViewBuilder(viewPanel);
	    
	    inputViewBuilder();
	    
	    //dispose on close while also opening mainwindow on close
//	    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//	    this.addWindowListener(new WindowAdapter() {
//	    	@Override
//	    	public void windowClosing(WindowEvent e) {
//	    		DBProxy.getInstance().updateFavoritedItems(App.getInstance().getFavorites());
//	    		homeView.makeVisible();
//	    	}
//	    });
//	    this.getContentPane().setBackground(Color.black);
//	    // set the jframe size and location, and make it visible
//	    this.setPreferredSize(new Dimension(1300, 600));
//	    this.pack();
//	    this.setLocationRelativeTo(null);
//	    this.setVisible(true);
	}

	
	public void titleBuilder() {
		JPanel titlePanel = new CustomPanel(Color.black, 10);
		titlePanel.setMaximumSize(new Dimension(titlePanel.getMaximumSize().width, titlePanel.getPreferredSize().height));
	    this.add(titlePanel);
	    
	    JLabel titleLabel = new JLabel("Favorited Items");
	    titleLabel.setForeground(Color.white);
	    titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
	    titlePanel.add(titleLabel);
	    
	    JPanel backPanel = new CustomPanel(Color.black, new FlowLayout(FlowLayout.LEFT));
	    backPanel.setBorder(BorderFactory.createEmptyBorder(0,20,0,20));
	    this.add(backPanel);
	    backButton = new CustomButton("Back", this);
	    backPanel.add(backButton);
	}
	
	
	public void fridgeViewBuilder(JPanel viewPanel) {
		JPanel fridgePanel = new CustomBoxPanel(Color.black, BoxLayout.Y_AXIS, 10);
	    viewPanel.add(fridgePanel);
	    
	    JPanel labelWrap = new CustomPanel(Color.black, new FlowLayout(FlowLayout.LEFT));
	    fridgePanel.add(labelWrap);
	    
	    JLabel fridgeLabel = new JLabel("Your Fridge Items");
	    fridgeLabel.setForeground(Color.white);
	    fridgeLabel.setFont(new Font("Arial", Font.BOLD, 16));
	    labelWrap.add(fridgeLabel);
	    
	    fridgeItems = new CompressedListView(App.getInstance().getInventory());
	    fridgeItems.setButtonPanelFlag(false);
	    fridgeItems.setListListener(this);
	    fridgePanel.add(fridgeItems);
	}
	
	
	
	public void favoritesViewBuilder(JPanel viewPanel) {
		JPanel favoritesPanel = new CustomBoxPanel(Color.black, BoxLayout.Y_AXIS, 10);
	    viewPanel.add(favoritesPanel);
	    
	    JPanel labelWrap = new CustomPanel(Color.black, new FlowLayout(FlowLayout.LEFT));
	    favoritesPanel.add(labelWrap);
	    
	    JLabel favoritesLabel = new JLabel("Your Favorites List");
	    favoritesLabel.setForeground(Color.white);
	    favoritesLabel.setFont(new Font("Arial", Font.BOLD, 16));
	    labelWrap.add(favoritesLabel);
	    
	    favoritesView = new ExpressiveListView(App.getInstance().getFavorites(), false);
	    favoritesPanel.add(favoritesView);
	}
	
	
	
	public void inputViewBuilder() {
		inputPanel = new CustomPanel(Color.black, 10);
	    this.add(inputPanel);
	    
	    quantityPanel = new CustomPanel(Color.black, 10);
	    
	    JLabel quantityLabel = new JLabel("Item Quantity: ");
	    quantityLabel.setForeground(Color.white);
	    quantityLabel.setFont(new Font("Arial", Font.BOLD, 16));
	    quantityPanel.add(quantityLabel);
	    
	    quantityInput = new StockInputField();
	    
	    addButton = new CustomButton("Add to Favorites List", this, 10);
	    addButton.setEnabled(false);
	    inputPanel.add(addButton);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backButton) {
			DBProxy.getInstance().updateFavoritedItems(App.getInstance().getFavorites());
			AppWindow.getWindow().loadPreviousWindow();
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
