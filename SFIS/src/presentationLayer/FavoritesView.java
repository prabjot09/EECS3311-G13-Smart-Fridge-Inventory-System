package presentationLayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

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
import domainLayer.FridgeItem;
import presentationLayer.itemListView.ButtonEmbeddedListView;
import presentationLayer.itemListView.CompressedListView;
import presentationLayer.itemListView.ListView;
import presentationLayer.swingExtensions.CustomBoxPanel;
import presentationLayer.swingExtensions.CustomButton;
import presentationLayer.swingExtensions.CustomPanel;
import presentationLayer.swingExtensions.CustomToggleButton;
import presentationLayer.swingExtensions.GridConstraintsSpec;
import domainLayer.Pair;
import domainLayer.StockableItem;
import domainLayer.StockableItemFactory;
import domainLayer.StoredItem;

public class FavoritesView extends AppFrameView implements ActionListener, ListSelectionListener{
	private CompressedListView fridgeItems;
	private List<ButtonEmbeddedListView> favoritesViews;
	
	private JPanel fridgePanel, favoritesWidePanel, favoritesSmallPanel, inputPanel;
	private JPanel viewPanel;
	
	private JButton backButton, addButton;
	private CustomToggleButton modeButton;
	
	private JPanel quantityPanel;
	private StockInputField quantityInput;
	
	public FavoritesView() {	
		this.setLayout(new GridBagLayout());
		this.favoritesViews = new ArrayList<>();
		
		titleBuilder();
	    
	    viewPanel = new CustomPanel(Color.black, new GridBagLayout(), 20);
	    this.add(viewPanel, GridConstraintsSpec.stretchableFillConstraints(0, 2, 1, 1, GridBagConstraints.BOTH));
	    
	    fridgeViewBuilder();
	    favoritesSmallPanel = favoritesViewBuilder(1);
	    favoritesWidePanel = favoritesViewBuilder(2);
	    inputViewBuilder();
	    
	    buildViewMode();
	}

	
	private void buildViewMode() {
		int componentCount = viewPanel.getComponentCount();
		for (int i = 0; i < componentCount; i++) {
			viewPanel.remove(0);
		}
		
		viewPanel.add(favoritesWidePanel, GridConstraintsSpec.stretchableFillConstraints(0, 0, 1, 1, GridBagConstraints.BOTH));
		viewPanel.revalidate();	
		viewPanel.repaint();
	}
	
	private void buildEditMode() {
		int componentCount = viewPanel.getComponentCount();
		for (int i = 0; i < componentCount; i++) {
			viewPanel.remove(0);
		}
		
		viewPanel.add(fridgePanel, GridConstraintsSpec.stretchableFillConstraints(0, 0, 1, 1, GridBagConstraints.BOTH));
		viewPanel.add(favoritesSmallPanel, GridConstraintsSpec.stretchableFillConstraints(1, 0, 1, 1, GridBagConstraints.BOTH));
		GridBagConstraints c = GridConstraintsSpec.stretchableFillConstraints(0, 1, 1, 0, GridBagConstraints.BOTH);
		c.gridwidth = 2;
		viewPanel.add(inputPanel, c);
		viewPanel.revalidate();
		viewPanel.repaint();
	}


	public void titleBuilder() {
		JPanel titlePanel = new CustomPanel(Color.black, new FlowLayout(FlowLayout.CENTER), 15);
	    this.add(titlePanel, GridConstraintsSpec.stretchableFillConstraints(0, 0, 1, 0, GridBagConstraints.BOTH));
	    
	    JLabel titleLabel = new JLabel("Favorited Items");
	    titleLabel.setForeground(Color.white);
	    titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
	    titleLabel.setToolTipText("Use this list to set items you absolutely need in your fridge. "
	    		+ "You can later use this list to help auto-generate a grocery list when you export the grocery list.");
	    titlePanel.add(titleLabel);
	    
	    JPanel navPanel = new CustomPanel(Color.black, new BorderLayout());
	    navPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 5, 20));
	    this.add(navPanel, GridConstraintsSpec.stretchableFillConstraints(0, 1, 1, 0, GridBagConstraints.BOTH));
	    backButton = new CustomButton("Back", this);
	    backButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
	    backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	    navPanel.add(backButton, BorderLayout.LINE_START);
	    
	    modeButton = new CustomToggleButton("Add New Items", "Leave Editing Mode", "Add New Items", this, 5);
	    modeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	    modeButton.initToggle(true);
	    navPanel.add(modeButton, BorderLayout.LINE_END);
	    
	}
	
	
	public void fridgeViewBuilder() {
		fridgePanel = new CustomBoxPanel(Color.black, BoxLayout.Y_AXIS, 10);
	    
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
	
	
	
	public JPanel favoritesViewBuilder(int cols) {
		JPanel favoritesPanel = new CustomPanel(Color.black, new GridBagLayout(), 10);
	    
	    JPanel labelWrap = new CustomPanel(Color.black, new FlowLayout(FlowLayout.LEFT));
	    favoritesPanel.add(labelWrap, GridConstraintsSpec.stretchableFillConstraints(0, 0, 1, 0, GridBagConstraints.BOTH));
	    
	    JLabel favoritesLabel = new JLabel("Your Favorites List");
	    favoritesLabel.setForeground(Color.white);
	    favoritesLabel.setFont(new Font("Arial", Font.BOLD, 16));
	    labelWrap.add(favoritesLabel);
	    
	    JPanel favoritesWrap = new CustomPanel(Color.black, new BorderLayout(), 5);
	    ButtonEmbeddedListView favoritesView = new ButtonEmbeddedListView(App.getInstance().getFavorites(), cols);
	    favoritesView.removeGroceryLink();
	    favoritesWrap.add(favoritesView);
	    favoritesPanel.add(favoritesWrap, GridConstraintsSpec.stretchableFillConstraints(0, 1, 1, 1, GridBagConstraints.BOTH));
	    
	    favoritesViews.add(favoritesView);
	    
	    return favoritesPanel;
	}
	
	
	
	public void inputViewBuilder() {
		inputPanel = new CustomPanel(Color.black, new FlowLayout(FlowLayout.CENTER));
	    
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
			this.saveData();
			AppWindow.getWindow().loadPreviousWindow();
			return;
		}
		else if (e.getSource() == addButton) {
			addHandler();
		}
		else if (e.getSource() == modeButton) {
			modeButton.toggle();
			for (ListView view: favoritesViews) {
				view.generateList(App.getInstance().getFavorites().getItems());
				view.removeGroceryLink();
			}
			
			if (modeButton.isOn())
				this.buildViewMode();
			else
				this.buildEditMode();
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
		inputPanel.repaint();
	}
	
	private void addHandler() {
		StoredItem selectedItem = (StoredItem) fridgeItems.getSelectedItem();
		if (selectedItem == null) {
			JOptionPane.showMessageDialog(this, "No item has been selected.", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		StockType type = selectedItem.getFoodItem().getStockType();
		Pair<StockType, Integer> input = quantityInput.getAmountValue(type.toString());
		
		if (input.getB() == null || input.getB() < 0 || !StockableItemFactory.createStockableItem(input.getA(), input.getB()).stockWithinBounds()) {
			JOptionPane.showMessageDialog(this, "Valid input quantity must be selected.", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		StoredItem newItem = selectedItem.copy();
		StockableItem stock = StockableItemFactory.createStockableItem(input.getA(), input.getB());
		newItem.setStockableItem(stock);
		
		try {
			FridgeItem fridgeItem = (FridgeItem) newItem;
			fridgeItem.setExpDate(null);
		} catch (Exception e) {}
		
		try {
			FavoritesList favorites = App.getInstance().getFavorites();
			favorites.add(newItem);
			
			for (ListView view: favoritesViews) {
				view.generateList(favorites.getItems());
				view.removeGroceryLink();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
	
	@Override
	public void saveData() {
		DBProxy.getInstance().updateFavoritedItems(App.getInstance().getFavorites());
	}
}
