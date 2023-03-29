package presentationLayer.itemAdditionUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import appLayer.App;
import domainLayer.DBProxy;
import domainLayer.DiscreteStockableItem;
import domainLayer.FoodItem;
import domainLayer.FoodItem.CreationType;
import domainLayer.FoodItem.StockType;
import presentationLayer.mainWindow;
import domainLayer.Fridge;
import domainLayer.FridgeItem;
import domainLayer.StockableItem;
import domainLayer.StoredItem;

public class AddSelectController implements ActionListener{
	private AddSelectView addSelectView;
	private mainWindow homeView;
	
	public AddSelectController(addWindow addWindowView, mainWindow homeView) {
		this.addSelectView = new AddSelectView(this);
		this.homeView = homeView;
		
		addWindowView.setAddingMethod(addSelectView);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton) e.getSource();
		if (clicked.getText() == "Search") {
			this.searchHandler();
		}
		else if (clicked.getText() == "Add Item") {
			this.addHandler();
		}
	}
	
	public void searchHandler() {
		String searchString = this.addSelectView.getSearchField();
		List<String> matches = DBProxy.getInstance().findItemDBItems(searchString);
		
		this.addSelectView.displayMatches(matches);
	}
	
	public boolean validateInput() {
		String itemName = this.addSelectView.getItemChosen();
		if (itemName == null) {
			JOptionPane.showMessageDialog(null, "Please select an item from the list.", "Warning", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		String amountStr = this.addSelectView.getAmountField();
		int amount;
		try {
			amount = Integer.parseInt(amountStr);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Please specify an integer amount", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	
	public void addHandler() {
		if (!this.validateInput()) {
			return;
		}
		
		LocalDate date = null;
		try {
			date = addSelectView.getDateInput();
		} catch (Exception e) {
			return;
		}

		String selectedItem = this.addSelectView.getItemChosen();		
		String amountString = this.addSelectView.getAmountField();
		int amount = Integer.parseInt(amountString);
		
		FoodItem itemDesc = new FoodItem();
		itemDesc.setName(selectedItem);
		itemDesc.setCreator(CreationType.PRESET);
		StockableItem stock = new DiscreteStockableItem(amount);
		itemDesc.setStockType(StockType.DISCRETE);
		
		FridgeItem item = new FridgeItem();
		item.setFoodItem(itemDesc);
		item.setStockableItem(stock);
		item.setExpDate(date);
		
		try {
			App.getInstance().getInventory().add(item);
			this.homeView.reloadLists();
			this.addSelectView.clearInput();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
}
