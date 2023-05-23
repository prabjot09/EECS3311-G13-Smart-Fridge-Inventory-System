package presentationLayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import domainLayer.FridgeItem;
import domainLayer.StockableItemFactory;
import domainLayer.StoredItem;
import presentationLayer.itemListView.ListView;
import presentationLayer.swingExtensions.CustomButton;
import presentationLayer.swingExtensions.CustomLabel;
import presentationLayer.swingExtensions.CustomPanel;
import presentationLayer.swingExtensions.DateInputField;
import presentationLayer.swingExtensions.GridConstraintsSpec;

public class EditItemWindow extends JFrame implements ActionListener {

	private ListView view;
	private StoredItem item;
	
	private StockInputField qtyInput, maxInput;
	private DateInputField dateInput;
	private JButton cancelButton, saveButton;
	
	private JPanel contentPanel;
	
	public EditItemWindow(ListView view, StoredItem item) {
		this.view = view;
		this.item = item;
		
		JPanel wholePanel = new CustomPanel(Color.black, new GridBagLayout(), 10);
		this.add(wholePanel);
		
		JPanel titlePanel = new CustomPanel(wholePanel.getBackground(), new FlowLayout(FlowLayout.CENTER), 5);
		wholePanel.add(titlePanel, GridConstraintsSpec.stretchableFillConstraints(0, 0, 1, 0, GridBagConstraints.BOTH));
		JLabel title = new JLabel("Edit Item");
		title.setFont(new Font("Arial", Font.BOLD, 28));
		title.setForeground(Color.white);
		titlePanel.add(title);
		
		JPanel contentWrap = new CustomPanel(wholePanel.getBackground(), new BorderLayout(), 5);
		wholePanel.add(contentWrap, GridConstraintsSpec.stretchableFillConstraints(0, 1, 1, 1, GridBagConstraints.BOTH));
		contentPanel = new CustomPanel(new Color(60, 60, 60), new GridBagLayout(), 10);
		contentWrap.add(contentPanel);
		
		JPanel nameWrap = new CustomPanel(contentPanel.getBackground(), new GridLayout(1, 2), 10);
		contentPanel.add(nameWrap, GridConstraintsSpec.stretchableFillConstraints(0, 0, 1, 0, GridBagConstraints.BOTH));
		CustomLabel nameLabel = new CustomLabel("Item Name: ", "Arial", Font.BOLD, 24, Color.white);
		JPanel nameLabelWrap = nameLabel.alignText(contentPanel.getBackground(), FlowLayout.CENTER, 3);
		nameWrap.add(nameLabelWrap);
		CustomLabel name = new CustomLabel(item.getFoodItem().getName(), "Arial", Font.PLAIN, 24, Color.white);
		JPanel nameTxtWrap = name.alignText(contentPanel.getBackground(), FlowLayout.CENTER, 3);
		nameWrap.add(nameTxtWrap);
		
		JPanel amtWrap = new CustomPanel(contentPanel.getBackground(), new GridLayout(1, 2), 10);
		contentPanel.add(amtWrap, GridConstraintsSpec.stretchableFillConstraints(0, 1, 1, 0, GridBagConstraints.BOTH));
		JPanel qtyWrap = new CustomPanel(contentPanel.getBackground(), new BorderLayout(), 10);
		amtWrap.add(qtyWrap);
		JPanel maxWrap = new CustomPanel(contentPanel.getBackground(), new BorderLayout(), 10);
		amtWrap.add(maxWrap);
		
		CustomLabel qtyLabel = new CustomLabel("Qty: ", "Arial", Font.BOLD, 20, Color.white);
		JPanel qtyLabelWrap = qtyLabel.alignText(contentPanel.getBackground(), FlowLayout.LEFT, 3, 10);
		qtyWrap.add(qtyLabelWrap, BorderLayout.LINE_START);
		qtyInput = new StockInputField();
		qtyInput.setFieldValue(item.getFoodItem().getStockType().toString(), item.getStockableItem().getStock());
		qtyWrap.add( qtyInput.getAmountField(item.getFoodItem().getStockType().toString()) );
		
		CustomLabel maxLabel = new CustomLabel("Max: ", "Arial", Font.BOLD, 20, Color.white);
		JPanel maxLabelWrap = maxLabel.alignText(contentPanel.getBackground(), FlowLayout.LEFT, 3, 10);
		maxWrap.add(maxLabelWrap, BorderLayout.LINE_START);
		maxInput = new StockInputField();
		maxInput.setFieldValue(item.getFoodItem().getStockType().toString(), item.getStockableItem().getMax());
		maxWrap.add( maxInput.getAmountField(item.getFoodItem().getStockType().toString()) );
		
		
		try {
			FridgeItem fItem = (FridgeItem) item;
			JPanel expPanel = new CustomPanel(contentPanel.getBackground(), new GridLayout(1, 2), 10);
			contentPanel.add(expPanel, GridConstraintsSpec.stretchableFillConstraints(0, 2, 1, 0, GridBagConstraints.BOTH));
			
			CustomLabel expLabel = new CustomLabel("Expiry: ", "Arial", Font.BOLD, 20, Color.white);
			JPanel expLabelWrap = new CustomPanel(contentPanel.getBackground(), new GridBagLayout(), 3);
			expLabelWrap.add(expLabel);
			expPanel.add(expLabelWrap);
			
			JPanel dateWrap = new CustomPanel(contentPanel.getBackground(), new FlowLayout(FlowLayout.CENTER), 3);
			expPanel.add(dateWrap);
			dateInput = new DateInputField(contentPanel.getBackground());
			dateInput.setSelectedDate(fItem.getExpDate());
			dateWrap.add(dateInput);
		} catch (Exception e) { }
		
		
		JPanel actionPanel = new CustomPanel(wholePanel.getBackground(), new GridBagLayout(), 15);
		wholePanel.add(actionPanel, GridConstraintsSpec.stretchableFillConstraints(0, 2, 1, 0, GridBagConstraints.BOTH));
		
		actionPanel.add(new CustomPanel(wholePanel.getBackground(), new BorderLayout()), GridConstraintsSpec.stretchableFillConstraints(0, 0, 1, 1, GridBagConstraints.BOTH));
		
		cancelButton = new CustomButton("Cancel", this, 10);
		actionPanel.add(cancelButton, GridConstraintsSpec.stretchableFillConstraints(1, 0, 0, 0, GridBagConstraints.BOTH));
		actionPanel.add(new CustomPanel(wholePanel.getBackground(), new BorderLayout()), GridConstraintsSpec.stretchableFillConstraints(2, 0, 0.2, 1, GridBagConstraints.BOTH));

		saveButton = new CustomButton("Save", this, 10);
		actionPanel.add(saveButton, GridConstraintsSpec.stretchableFillConstraints(3, 0, 0, 0, GridBagConstraints.BOTH));
		actionPanel.add(new CustomPanel(wholePanel.getBackground(), new BorderLayout()), GridConstraintsSpec.stretchableFillConstraints(4, 0, 1, 1, GridBagConstraints.BOTH));
		
		this.getContentPane().setBackground(Color.black);
		this.setTitle("Editing Item: " + item.getFoodItem().getName());
	    this.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cancelButton) {
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
			this.dispose();
		}
		else if (e.getSource() == saveButton) {
			if (!validInput())
				return;
			
			String type = item.getFoodItem().getStockType().toString();
			int max = maxInput.getAmountValue(type).getB();
			int qty = qtyInput.getAmountValue(type).getB();
			
			if (qty > item.getStockableItem().getMax()) {
				item.getStockableItem().setMax(max);
				item.getStockableItem().setStock(qty);				
			} else {
				item.getStockableItem().setStock(qty);
				item.getStockableItem().setMax(max);
			}
			
			try {
				((FridgeItem) item).setExpDate(dateInput.isUnused() ? null : dateInput.getDate());
			} catch (Exception ex) {}
			
			view.updateItem(item);
			
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
			this.dispose();
		}
	}

	private boolean validInput() {
		String type = item.getFoodItem().getStockType().toString();
		if (qtyInput.getAmountValue(type) == null || qtyInput.getAmountValue(type).getB() == null) {
			JOptionPane.showMessageDialog(null, "Please specify an amount for the Qty field", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		int qty = qtyInput.getAmountValue(type).getB(); 
		if (qty < 0 || !StockableItemFactory.createStockableItem(item.getFoodItem().getStockType(), qty).stockWithinBounds()) {
			JOptionPane.showMessageDialog(null, "The amount for Qty field is out of bounds.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if (maxInput.getAmountValue(type) == null || maxInput.getAmountValue(type).getB() == null) {
			JOptionPane.showMessageDialog(null, "Please specify an amount for the Max field", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		int max = maxInput.getAmountValue(type).getB(); 
		if (max < 0 || !StockableItemFactory.createStockableItem(item.getFoodItem().getStockType(), max).stockWithinBounds()) {
			JOptionPane.showMessageDialog(null, "The amount for Max field is out of bounds.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if (qty > max) {
			JOptionPane.showMessageDialog(null, "The amount for Qty field should be less than the Max field.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if (!dateInput.isUnused() && dateInput.getDate() == null) {
			return false;
		}
		
		return true;
	}
	
}
