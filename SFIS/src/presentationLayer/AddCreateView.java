package presentationLayer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import domainLayer.FoodItem;
import domainLayer.FoodItem.StockType;
import presentationLayer.swingExtensions.CustomBoxPanel;
import presentationLayer.swingExtensions.CustomButton;
import presentationLayer.swingExtensions.CustomPanel;
import presentationLayer.swingExtensions.DateInputField;
import domainLayer.Pair;

public class AddCreateView extends JPanel{
	private JTextField nameField;
	private JComboBox<String> amountTypeField;
	
	private StockInputField amountField;
	private DateInputField dateField;
	private JPanel amountPanel;
	
	private Component amountEntry;

	public AddCreateView(ActionListener listener) {
		BoxLayout overallLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(overallLayout);
		
		JPanel inputPanel = new CustomBoxPanel(Color.black, BoxLayout.Y_AXIS, 10);
		this.add(inputPanel);
	    
		JPanel namePanel = new CustomPanel(Color.black, null);
		inputPanel.add(namePanel);
		
		JLabel nameLabel = new JLabel("Item Name: ");
	    nameLabel.setForeground(Color.white);
	    nameLabel.setFont(new Font("Arial", Font.BOLD, 24));
	    nameLabel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	    namePanel.add(nameLabel);
	    
		JTextField nameField = new JTextField("");
		nameField.setFont(new Font("Arial", Font.PLAIN, 16));
		nameField.setBackground(Color.gray);
		nameField.setBounds(0,100,300,500);
	    nameField.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		nameField.setPreferredSize(new Dimension(300,50));
	    this.nameField = nameField;
	    namePanel.add(nameField);
	    
	    amountPanel = new CustomPanel(Color.black, null);
	    inputPanel.add(amountPanel);
	    
	    String[] values = {"Amount Type", StockType.CONTINUOUS.toString(), StockType.DISCRETE.toString()};
	    amountTypeField = new JComboBox<String>(values);
	    amountTypeField.setFont(new Font("Arial", Font.PLAIN, 16));
	    amountTypeField.setPreferredSize(new Dimension(300,50));
	    amountTypeField.addActionListener(listener);
	    amountPanel.add(amountTypeField);
	    
	    amountField = new StockInputField();
	    amountEntry = null;
	    
	    JPanel expiryPanel = new CustomPanel(Color.black, null);
		inputPanel.add(expiryPanel);
		
		JLabel expiryLabel = new JLabel("[Optional] Expiry Date: ");
		expiryLabel.setForeground(Color.white);
		expiryLabel.setFont(new Font("Arial", Font.BOLD, 20));
		expiryLabel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	    expiryPanel.add(expiryLabel);
	    
		dateField = new DateInputField();
		dateField.setBackground(Color.black);
		expiryPanel.add(dateField);
	    
	    JPanel buttonPanel = new CustomPanel(Color.black, 10);
		
	    JButton addButton = new CustomButton("Add Item", listener, 20);
		buttonPanel.add(addButton);
		
		this.add(buttonPanel);
	    
	}
	
	public String getItemName() {
		return this.nameField.getText();
	}
	
	public Pair<StockType, Integer> getAmount() {
		String selectedType = (String) amountTypeField.getSelectedItem();
		return this.amountField.getAmountValue(selectedType);
	}
	
	public String getAmountType() {
		if (this.amountTypeField.getSelectedIndex() == 0)
			return null;
		
		return (String) this.amountTypeField.getSelectedItem();
	}

	public void clearInput() {
		this.amountField.clear();
		this.nameField.setText("");
		this.amountTypeField.setSelectedIndex(0);
	}
	
	public void setAmountEntry() {
		if (amountEntry != null) 
			amountPanel.remove(amountEntry);
		
		String amountType = (String) amountTypeField.getSelectedItem();
		amountEntry = amountField.getAmountField(amountType);
		
		if (amountEntry != null) {
			amountPanel.add(amountEntry);
		}
		
		amountPanel.revalidate();
		amountPanel.repaint();
	}
	
	
	public LocalDate getDateInput() throws Exception {
		if (dateField.isUnused()) 
			return null;
		
		LocalDate date = dateField.getDate();
		if (date == null)
			throw new Exception();
		
		return date;
	}
}
