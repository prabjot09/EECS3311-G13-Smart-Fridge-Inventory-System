package presentationLayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import domainLayer.DiscreteStockableItem;
import domainLayer.FoodItem;
import domainLayer.Fridge;
import domainLayer.FridgeItem;
import domainLayer.StockableItem;

public class AddCreateController implements ActionListener {
	private mainWindow homeView;
	private AddCreateView addCreateView;
	private Fridge fridge;

	public AddCreateController(addWindow addWindowView, mainWindow homeView, Fridge fridge) {
		this.addCreateView = new AddCreateView(this);
		this.homeView = homeView;
		this.fridge = fridge;
		
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
			JOptionPane.showMessageDialog(null, "Please enter a valid name", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		String amountStr = this.addCreateView.getAmount();
		int amount;
		try {
			amount = Integer.parseInt(amountStr);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Please specify an integer amount", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		FoodItem itemDesc = new FoodItem();
		itemDesc.setName(itemName);
		StockableItem stock = new DiscreteStockableItem(amount);
		
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
