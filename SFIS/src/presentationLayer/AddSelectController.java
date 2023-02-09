package presentationLayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import domainLayer.DBProxy;
import domainLayer.DiscreteStockableItem;
import domainLayer.FoodItem;
import domainLayer.FridgeItem;
import domainLayer.StockableItem;
import domainLayer.StoredItem;

public class AddSelectController implements ActionListener{
	AddSelectView addSelectView;
	mainWindow homeView;
	DBProxy database;
	
	public AddSelectController(DBProxy database, addWindow addWindowView, mainWindow homeView) {
		this.addSelectView = new AddSelectView(database, this);
		this.database = database;
		this.homeView = homeView;
		
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
		List<String> matches = database.findItemDBItems(searchString);
		
		this.addSelectView.displayMatches(matches);
	}
	
	public void addHandler() {
		String selectedItem = this.addSelectView.getItemChosen();
		if (selectedItem == null) {
			System.out.println("Please select an item from the list");
			return;
		}
		
		String amountString = this.addSelectView.getAmountField();
		Integer amount;
		try {
			amount = Integer.parseInt(amountString);
		} catch (Exception e) {
			System.out.println("Please specify an integer amount");
			return;
		}
		
		FoodItem itemDesc = new FoodItem();
		itemDesc.setName(selectedItem);
		StockableItem stock = new DiscreteStockableItem(amount.intValue());
		
		FridgeItem item = new FridgeItem();
		item.setFoodItem(itemDesc);
		item.setStockableItem(stock);
		
		this.database.addItem(item);
		System.out.println(this.database.loadItems().size());
		this.homeView.refreshList();
	}
}
