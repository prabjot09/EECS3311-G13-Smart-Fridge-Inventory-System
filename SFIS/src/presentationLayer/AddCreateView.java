package presentationLayer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import domainLayer.FoodItem;


public class AddCreateView extends JPanel{
	private JTextField nameField;
	private JTextField amountField;
	private JComboBox amountTypeField;

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
	    
		JTextField nameField = new JTextField("Item Name");
		nameField.setFont(new Font("Arial", Font.PLAIN, 16));
		nameField.setBackground(Color.gray);
		nameField.setBounds(0,100,300,500);
	    nameField.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		nameField.setPreferredSize(new Dimension(300,50));
	    this.nameField = nameField;
	    namePanel.add(nameField);
	    
	    JPanel amountPanel = new JPanel();
	    amountPanel.setBackground(Color.black);
	    inputPanel.add(amountPanel);
	    
	    
	    FoodItem.StockType[] typeVals = FoodItem.StockType.values();
	    String[] values = new String[1 + typeVals.length];
	    values[0] = "Amount Type";
	    for (int i = 0; i < typeVals.length; i++) {
	    	values[i+1] = typeVals[i].toString();
	    }
	    JComboBox<String> typeDropDown = new JComboBox<String>(values);
	    typeDropDown.setFont(new Font("Arial", Font.PLAIN, 16));
	    typeDropDown.setPreferredSize(new Dimension(300,50));
	    this.amountTypeField = typeDropDown;
	    amountPanel.add(typeDropDown);
	    
	    JTextField amountField = new JTextField("Amount Remaining");
	    amountField.setFont(new Font("Arial", Font.PLAIN, 16));
	    amountField.setBackground(Color.gray);
		amountField.setBounds(0,100,300,500);
	    amountField.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		amountField.setPreferredSize(new Dimension(300,50));
	    this.amountField = amountField;
	    amountPanel.add(amountField);
	    
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
	
	public String getAmount() {
		return this.amountField.getText();
	}
	
	public int getAmountTypeIndex() {
		return this.amountTypeField.getSelectedIndex();
	}

	public void clearInput() {
		this.amountField.setText("");
		this.nameField.setText("");
		this.amountTypeField.setSelectedIndex(0);
	}
	
}
