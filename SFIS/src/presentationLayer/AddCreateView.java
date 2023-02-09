package presentationLayer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import domainLayer.DBProxy;

public class AddCreateView extends JPanel{
	private JTextField nameField;
	private JTextField amountField;
	private JList<String> matchList;

	public AddCreateView(DBProxy database, ActionListener listener) {
		BoxLayout overallLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(overallLayout);
		
		JPanel inputPanel = new JPanel();
		inputPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		inputPanel.setBackground(Color.black);
	    
		JTextField nameField = new JTextField("Item Name");
		nameField.setFont(new Font("Arial", Font.PLAIN, 16));
		nameField.setBackground(Color.gray);
		nameField.setBounds(0,100,300,500);
		nameField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		nameField.setPreferredSize(new Dimension(300,50));
	    this.nameField = nameField;
	    inputPanel.add(nameField);
	    
	    JTextField amountField = new JTextField("Amount Remaining");
	    amountField.setFont(new Font("Arial", Font.PLAIN, 16));
	    amountField.setBackground(Color.gray);
		amountField.setBounds(0,100,300,500);
		amountField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		amountField.setPreferredSize(new Dimension(300,50));
	    this.amountField = amountField;
	    inputPanel.add(amountField);
	    this.add(inputPanel);
	    
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
	
	
}
