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
import domainLayer.StockableItemFactory;

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
		JButton clicked;
		
		try {
			clicked = (JButton) e.getSource();
		} catch (Exception exception) {
			System.out.println(exception.getStackTrace());
			return;
		}
		
		if (clicked.getText() == "Add") {
			this.addHandler();
		}

	}
	
	public boolean validateInput() {
		String itemName = this.addCreateView.getItemName();
		if (itemName.equals("Item Name") || itemName.equals("")) {
			JOptionPane.showMessageDialog(null, "Please enter a valid name", "Warning", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		String amountStr = this.addCreateView.getAmount();
		int amount;
		try {
			amount = Integer.parseInt(amountStr);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Please specify an integer amount", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		int amountType = this.addCreateView.getAmountTypeIndex();
		if (amountType == 0) {
			JOptionPane.showMessageDialog(null, "Please specify the amount type", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	
	public void addHandler() {
		if (this.validateInput() == false)
			return;
		
		FoodItem itemDesc = new FoodItem();
		itemDesc.setName(this.addCreateView.getItemName());
		int amountType = this.addCreateView.getAmountTypeIndex() - 1;
		int amount = Integer.parseInt(this.addCreateView.getAmount());
		StockableItem stock = StockableItemFactory.createStockableItem(amountType, amount);
		
		FridgeItem item = new FridgeItem();
		item.setFoodItem(itemDesc);
		item.setStockableItem(stock);
		
		try {
			this.fridge.add(item);
			this.homeView.addNewItem();
			this.addCreateView.clearInput();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
	}

}
