package presentationLayer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import domainLayer.DBProxy;
import presentationLayer.swingExtensions.CustomButton;
import presentationLayer.swingExtensions.CustomPanel;
import presentationLayer.swingExtensions.DateInputField;
import presentationLayer.swingExtensions.PromptText;


public class AddSelectView extends JPanel {
	private JTextField searchField;
	private JTextField amountField;
	private JList<String> matchList;
	private DateInputField dateField;
	
	public AddSelectView(ActionListener listener) {
		BoxLayout overallLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(overallLayout);
		
		JPanel searchPanel = new CustomPanel(Color.black, 10);
	    
		JTextField search = new PromptText("Find Item in Database");
	    search.setFont(new Font("Arial", Font.PLAIN, 16));
	    search.setBackground(Color.gray);
	    search.setBounds(0,100,300,500);
	    search.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	    search.setPreferredSize(new Dimension(300,50));
	    this.searchField = search;
	    searchPanel.add(search);
	    
	    JButton searchButton = new CustomButton("Search", listener, 50, 150);
	    searchPanel.add(searchButton);
	    
	    this.add(searchPanel);
	    
		JList<String> itemList = new JList<String>();
		itemList.setBackground(Color.gray);
		itemList.setFont(new Font("Arial", Font.BOLD, 18));
		itemList.setPreferredSize(new Dimension(440, 200));
		itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.matchList = itemList;
		
		JScrollPane listPanel = new JScrollPane(matchList);
		listPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		listPanel.setBackground(Color.black);
		this.add(listPanel);
		
		JPanel addPanel = new CustomPanel(Color.black, 10);
		this.add(addPanel);
		
		JTextField amountField = new PromptText("Quantity");
	    amountField.setFont(new Font("Arial", Font.PLAIN, 16));
	    amountField.setBackground(Color.gray);
	    amountField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    amountField.setMinimumSize(new Dimension(300, 0));
	    this.amountField = amountField;
	    addPanel.add(amountField);
		
		JButton addButton = new CustomButton("Add Item", listener, 12);
		addPanel.add(addButton);
		
		
		JPanel expiryPanel = new CustomPanel(Color.black, null);
		this.add(expiryPanel);
		
		JLabel expiryLabel = new JLabel("[Optional] Expiry Date: ");
		expiryLabel.setForeground(Color.white);
		expiryLabel.setFont(new Font("Arial", Font.BOLD, 20));
		expiryLabel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	    expiryPanel.add(expiryLabel);
	    
		dateField = new DateInputField();
		dateField.setBackground(Color.black);
		expiryPanel.add(dateField);
		
		
		this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	    this.setBackground(Color.black);		
	}
	
	public String getSearchField() {
		return this.searchField.getText();
	}
	
	public String getAmountField() {
		return this.amountField.getText();
	}
	
	public String getItemChosen() {
		return this.matchList.getSelectedValue();
	}
	
	public void displayMatches(List<String> matches) {
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		for (String match: matches) {
			listModel.addElement(match);
		}
		
		this.matchList.setModel(listModel);
		this.matchList.setPreferredSize(new Dimension(440, 30 * matches.size()));
		this.matchList.revalidate();
	}

	public void clearInput() {
		this.amountField.setText("");
		this.searchField.setText("");
		this.displayMatches(DBProxy.getInstance().findItemDBItems(this.getSearchField()));
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
