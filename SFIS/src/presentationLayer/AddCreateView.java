package presentationLayer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import domainLayer.Pair;

public class AddCreateView extends JPanel{
	private JTextField nameField;
	private JComboBox<String> amountTypeField;
	
	private StockInputField amountField;
	private JPanel amountPanel;
	
	private Component amountEntry;

	public AddCreateView(ActionListener listener) {
		BoxLayout overallLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(overallLayout);
		
		JPanel inputPanel = new JPanel();
		inputPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
		inputPanel.setBackground(Color.black);
		this.add(inputPanel);
	    
		JPanel namePanel = new JPanel();
		namePanel.setBackground(Color.black);
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
	    
	    amountPanel = new JPanel();
	    amountPanel.setBackground(Color.black);
	    inputPanel.add(amountPanel);
	    
	    String[] values = {"Amount Type", StockType.CONTINUOUS.toString(), StockType.DISCRETE.toString()};
	    amountTypeField = new JComboBox<String>(values);
	    amountTypeField.setFont(new Font("Arial", Font.PLAIN, 16));
	    amountTypeField.setPreferredSize(new Dimension(300,50));
	    amountTypeField.addActionListener(listener);
	    amountPanel.add(amountTypeField);
	    
	    amountField = new StockInputField();
	    amountEntry = null;
	    
	    JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		buttonPanel.setBackground(Color.black);
		
	    JButton addButton = new JButton("Add");
	    addButton.addActionListener(listener);
		addButton.setPreferredSize(new Dimension(100, 50));
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
	
}
