package presentationLayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import domainLayer.DiscreteStockableItem;
import domainLayer.FoodItem;
import domainLayer.FoodItem.StockType;
import domainLayer.Fridge;
import domainLayer.FridgeItem;
import domainLayer.Pair;
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
		try {
			JButton clicked = (JButton) e.getSource();
			this.addHandler();
		} catch (Exception exception) {
			this.addCreateView.setAmountEntry();
		}
	}
	
	public boolean validateInput() {
		String itemName = this.addCreateView.getItemName();
		if (itemName.equals("Item Name") || itemName.equals("")) {
			JOptionPane.showMessageDialog(null, "Please enter a valid name", "Warning", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		String amountType = this.addCreateView.getAmountType();
		if (amountType == null) {
			JOptionPane.showMessageDialog(null, "Please specify the amount type", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		Pair<StockType, Integer> amount = this.addCreateView.getAmount();
		if (amount.getB() == null) {
			JOptionPane.showMessageDialog(null, "Please specify a valid value", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	
	public void addHandler() {
		if (this.validateInput() == false)
			return;
		
		FoodItem itemDesc = new FoodItem();
		itemDesc.setName(this.addCreateView.getItemName());
		itemDesc.setCreator(FoodItem.CreationType.USER);
		
		Pair<StockType, Integer> amountData = this.addCreateView.getAmount();
		StockableItem stock = StockableItemFactory.createStockableItem(amountData.getA(), amountData.getB());
		itemDesc.setStockType(amountData.getA());
		
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
