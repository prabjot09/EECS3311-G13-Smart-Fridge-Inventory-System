package presentationLayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import domainLayer.DBProxy;
import domainLayer.DiscreteStockableItem;
import domainLayer.FoodItem;
import domainLayer.Fridge;
import domainLayer.FridgeItem;
import domainLayer.StockableItem;
import domainLayer.StoredItem;

public class AddSelectController implements ActionListener{
	private AddSelectView addSelectView;
	private mainWindow homeView;
	private Fridge fridge;
	
	public AddSelectController(addWindow addWindowView, mainWindow homeView, Fridge fridge) {
		this.addSelectView = new AddSelectView(this);
		this.homeView = homeView;
		this.fridge = fridge;
		
		addWindowView.setAddingMethod(addSelectView);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton) e.getSource();
		if (clicked.getText() == "Search") {
			this.searchHandler();
		}
		else if (clicked.getText() == "Add") {
			this.addHandler();
		}
	}
	
	public void searchHandler() {
		String searchString = this.addSelectView.getSearchField();
		List<String> matches = DBProxy.getInstance().findItemDBItems(searchString);
		
		this.addSelectView.displayMatches(matches);
	}
	
	public void addHandler() {
		String selectedItem = this.addSelectView.getItemChosen();
		if (selectedItem == null) {
			JOptionPane.showMessageDialog(null, "Please select an item from the list", "Notice", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		String amountString = this.addSelectView.getAmountField();
		Integer amount;
		try {
			amount = Integer.parseInt(amountString);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Please specify an integer amount", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		FoodItem itemDesc = new FoodItem();
		itemDesc.setName(selectedItem);
		StockableItem stock = new DiscreteStockableItem(amount.intValue());
		
		FridgeItem item = new FridgeItem();
		item.setFoodItem(itemDesc);
		item.setStockableItem(stock);
		
		try {
			this.fridge.add(item);
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		this.homeView.addNewItem();
	}
}
