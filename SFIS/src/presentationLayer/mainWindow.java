package presentationLayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import domainLayer.DBProxy;
import domainLayer.Fridge;
import domainLayer.StoredItem;

public class mainWindow extends JFrame implements ActionListener{
	  private JFrame jframe = new JFrame("SFIS");
	  private JPanel panel;
	  private JPanel searchPanel;
	  private JLabel titleLabel;
	  private JTextField search;
	  private JList<String> list;
	  private JButton addButton, searchButton,incButton,decButton;
	  private JScrollPane scroll;
	  private DefaultListModel<String> fridgeList;
	  private Fridge inv;
	public mainWindow() {
	    // create our jframe as usual
		List<StoredItem> items = DBProxy.getInstance().loadItems();
		inv = new Fridge(items);
			
		panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	    panel.setBackground(Color.black);
		jframe.add(panel,BorderLayout.NORTH);
	
	    
	    titleLabel = new JLabel("Smart Fridge Tracker");
	    titleLabel.setForeground(Color.white);
	    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
	    //search and list panel
	    searchPanel = new JPanel();
	    searchPanel.setBackground(Color.black);
	    searchPanel.setBounds(0,100,1000,500);
	    panel.add(titleLabel);
	    
	    search = new JTextField();
	    search.setFont(new Font("Arial", Font.PLAIN, 16));
	    search.setBackground(Color.gray);
	    search.setBounds(0,100,300,500);
	    search.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	    search.setPreferredSize(new Dimension(300,50));
	    searchPanel.add(search);
	    jframe.add(searchPanel);
	    
	    
	    searchButton = new JButton("Search");
	    searchButton.addActionListener(this);
	    searchButton.setPreferredSize(new Dimension(100,50));
	    searchPanel.add(searchButton);
	    
	    
	    addButton = new JButton("+");
	    addButton.addActionListener(this);
	    addButton.setPreferredSize(new Dimension(50,50));
	    searchPanel.add(addButton);
	    
	   
	    
	    
	    
	    fridgeList = new DefaultListModel<String>();
	    for (int x = 0; x < inv.getFridgeItems().size(); x++) {
	    	fridgeList.addElement(inv.getFridgeItems().get(x).getFoodItem().getName() + ": " + 
	        inv.getFridgeItems().get(x).getStockableItem().getStock() + " units");
	    }
	    list = new JList<String>(fridgeList);
	    list.setBackground(Color.gray);
	    list.setFont(new Font("Arial", Font.BOLD, 24));
	    list.setPreferredSize(new Dimension(800, 220));
	    list.setBounds(0,300,1000,500);
	    scroll = new JScrollPane(list);	    
	    searchPanel.add(scroll);
	    
	    
	    incButton = new JButton("Increment");
	    incButton.addActionListener(this);
	    incButton.setPreferredSize(new Dimension(200,50));
	    searchPanel.add(incButton);
	    
	    decButton = new JButton("Decrement");
	    decButton.addActionListener(this);
	    decButton.setPreferredSize(new Dimension(200,50));
	    searchPanel.add(decButton);
	    
	   
	    
	    
	    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    jframe.getContentPane().setBackground(Color.black);
	    // set the jframe size and location, and make it visible
	    jframe.setPreferredSize(new Dimension(1000, 600));
	    jframe.pack();
	    jframe.setLocationRelativeTo(null);
	    jframe.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == addButton) {
		new AddWindowController(this, inv);
		}
		
		if (e.getSource() == searchButton) {		
			mainSearchHandler();
		}
		
		if (e.getSource() == incButton) {		
			String incHolder = (fridgeList.getElementAt(list.getSelectedIndex()));
			String incer = incHolder.substring(0, incHolder.indexOf(":"));
			for (int x = 0; x < inv.getFridgeItems().size(); x++) {
				if (inv.getFridgeItems().get(x).getFoodItem().getName().toLowerCase().contains(incer.toLowerCase())) {
					inv.getFridgeItems().get(x).executeIncrement(1);
					 fridgeList.set(x,inv.getFridgeItems().get(x).getFoodItem().getName() 
							 + ": " + inv.getFridgeItems().get(x).getStockableItem().getStock() + " units");
				}
			}
			DBProxy.getInstance().updateFridge(inv);
			//this.list.setModel(this.fridgeList);
			
		}
		
		if (e.getSource() == decButton) {		
			String decHolder = (fridgeList.getElementAt(list.getSelectedIndex()));
			String decer = decHolder.substring(0, decHolder.indexOf(":"));
			for (int x = 0; x < inv.getFridgeItems().size(); x++) {
				if (inv.getFridgeItems().get(x).getFoodItem().getName().toLowerCase().contains(decer.toLowerCase())) {
					if (inv.getFridgeItems().get(x).getStockableItem().getStock() < 1) {
						JOptionPane.showMessageDialog(null, "Item is at 0 stock", "Notice", JOptionPane.WARNING_MESSAGE);
						return;
					}
					inv.getFridgeItems().get(x).executeDecrement(1);
					fridgeList.set(x,inv.getFridgeItems().get(x).getFoodItem().getName() 
							 + ": " + inv.getFridgeItems().get(x).getStockableItem().getStock() + " units");
				}
			}
			//this.list.setModel(this.fridgeList);
			DBProxy.getInstance().updateFridge(inv);			
		}
	}
	
	
	public void mainSearchHandler() {
		//We want to ensure we can search our JList, and let it return to its former state if the search is cleared.
		//We do this by passing our inventory and using .contains to add eveyrthing that matches to our JList 
		String searchString = search.getText();
		DefaultListModel<String> updatedFridgeList = new DefaultListModel<String>();
		 for (int x = 0; x < inv.getFridgeItems().size(); x++) {
			 if (inv.getFridgeItems().get(x).getFoodItem().getName().toLowerCase().contains(searchString.toLowerCase())) {
				 updatedFridgeList.addElement(inv.getFridgeItems().get(x).getFoodItem().getName() 
				 + ": " + inv.getFridgeItems().get(x).getStockableItem().getStock() + " units");
			 }
		 }
		
		 this.fridgeList = updatedFridgeList;
		 this.list.setModel(updatedFridgeList);
		
		 this.list.revalidate();
		 this.scroll.revalidate();
		 
	}
	
	
	public void refreshList() {
		List<StoredItem> items = this.inv.getFridgeItems();
		int x = inv.getFridgeItems().size() - 1;
		
	    
	    fridgeList.add(fridgeList.size(), inv.getFridgeItems().get(x).getFoodItem().getName() + ": " + 
	    inv.getFridgeItems().get(x).getStockableItem().getStock() + " units");
	    
	    
	    this.list.setModel(fridgeList);
	    this.list.setPreferredSize(new Dimension(750, 30 * items.size()));;
	    this.list.revalidate();
	    this.scroll.revalidate();
	    
	}
}
