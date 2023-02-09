package presentationLayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import domainLayer.DBProxy;
import domainLayer.DiscreteStockableItem;
import domainLayer.FoodItem;
import domainLayer.FridgeItem;
import domainLayer.StockableItem;

public class AddCreateController implements ActionListener {
	private DBProxy database;
	private mainWindow homeView;
	private AddCreateView addCreateView;

	public AddCreateController(DBProxy database, addWindow addWindowView, mainWindow homeView) {
		this.addCreateView = new AddCreateView(database, this);
		this.database = database;
		this.homeView = homeView;
		
		addWindowView.setAddingMethod(addCreateView);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton) e.getSource();
		
		if (clicked.getText() == "Add") {
			this.addHandler();
		}

	}
	
	public void addHandler() {
		String itemName = this.addCreateView.getItemName();
		if (itemName.equals("Item Name") || itemName.equals("")) {
			System.out.println("Please enter a valid name.");
			return;
		}
		
		String amountStr = this.addCreateView.getAmount();
		int amount;
		try {
			amount = Integer.parseInt(amountStr);
		} catch (Exception e) {
			System.out.println("Please enter a valid integer quantity");
			return;
		}
		
		FoodItem itemDesc = new FoodItem();
		itemDesc.setName(itemName);
		StockableItem stock = new DiscreteStockableItem(amount);
		
		FridgeItem item = new FridgeItem();
		item.setFoodItem(itemDesc);
		item.setStockableItem(stock);
		
		this.database.addItem(item);
		this.homeView.refreshList();
		
		
	}

}
