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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
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
	  private JButton addButton;
	  private DBProxy db;
	public mainWindow(DBProxy db) {
	    // create our jframe as usual
		this.db = db;
		List<StoredItem> items = db.loadItems();
		Fridge inv = new Fridge(items);
			
		inv.setFridgeItems(db.loadItems());
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
	    
	    search = new JTextField("Find Item in Fridge");
	    search.setFont(new Font("Arial", Font.PLAIN, 16));
	    search.setBackground(Color.gray);
	    search.setBounds(0,100,300,500);
	    search.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	    search.setPreferredSize(new Dimension(300,50));
	    searchPanel.add(search);
	    jframe.add(searchPanel);
	    
	    addButton = new JButton("+");
	    addButton.addActionListener(this);
	    addButton.setPreferredSize(new Dimension(50,50));
	    searchPanel.add(addButton);
	    
	    
	    
	    DefaultListModel<String> fridgeList = new DefaultListModel<String>();
	    for (int x = 0; x < inv.getFridgeItems().size(); x++) {
	    	fridgeList.addElement(inv.getFridgeItems().get(x).getFoodItem().getName() + ": " + 
	        inv.getFridgeItems().get(x).getStockableItem().getStock() + " units");
	    }
	    list = new JList<String>(fridgeList);
	    list.setBackground(Color.gray);
	    list.setFont(new Font("Arial", Font.BOLD, 24));
	    list.setPreferredSize(new Dimension(800,500));
	    list.setBounds(0,300,1000,500);
	    JScrollPane scroll = new JScrollPane(list);	    
	    jframe.add(scroll);
	    searchPanel.add(scroll);
	    
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
		new AddWindowController(db, this);
	}
	
	public void refreshList() {
		List<StoredItem> items = this.db.loadItems();
		Fridge inv = new Fridge(items);
		
		DefaultListModel<String> fridgeList = new DefaultListModel<String>();
	    for (int x = 0; x < inv.getFridgeItems().size(); x++) {
	    	fridgeList.addElement(inv.getFridgeItems().get(x).getFoodItem().getName() + ": " + 
	        inv.getFridgeItems().get(x).getStockableItem().getStock() + " units");
	    }
	    
	    this.list.setModel(fridgeList);
	    System.out.println(items.size());
	    this.list.revalidate();
	}
}
