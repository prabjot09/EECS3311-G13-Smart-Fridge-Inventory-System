package presentationLayer.itemAdditionUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import appLayer.App;
import domainLayer.DiscreteStockableItem;
import domainLayer.FoodItem;
import domainLayer.FoodItem.StockType;
import presentationLayer.mainWindow;
import domainLayer.Fridge;
import domainLayer.FridgeItem;
import domainLayer.Pair;
import domainLayer.StockableItem;
import domainLayer.StockableItemFactory;

public class AddCreateController implements ActionListener {
	private mainWindow homeView;
	private AddCreateView addCreateView;

	public AddCreateController(addWindow addWindowView, mainWindow homeView) {
		this.addCreateView = new AddCreateView(this);
		this.homeView = homeView;
		
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
		
		if (amount.getB() < 0 || !StockableItemFactory.createStockableItem(amount.getA(), amount.getB()).stockWithinBounds()) {
			JOptionPane.showMessageDialog(null, "Item Quantity is out of Bounds", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	
	public void addHandler() {
		if (this.validateInput() == false)
			return;
		
		LocalDate date;
		try {
			date = addCreateView.getDateInput();
		} catch (Exception e) {
			return;
		}
		
		FoodItem itemDesc = new FoodItem();
		itemDesc.setName(this.addCreateView.getItemName());
		itemDesc.setCreator(FoodItem.CreationType.USER);
		
		Pair<StockType, Integer> amountData = this.addCreateView.getAmount();
		StockableItem stock = StockableItemFactory.createStockableItem(amountData.getA(), amountData.getB());
		itemDesc.setStockType(amountData.getA());

		FridgeItem item = new FridgeItem();
		item.setFoodItem(itemDesc);
		item.setStockableItem(stock);
		item.setExpDate(date);
		
		try {
			App.getInstance().getInventory().add(item);
			this.homeView.reloadLists();
			this.addCreateView.clearInput();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
	}

}
