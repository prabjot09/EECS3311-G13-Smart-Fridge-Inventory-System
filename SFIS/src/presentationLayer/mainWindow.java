package presentationLayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
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
import domainLayer.FridgeItem;
import domainLayer.StoredItem;

public class mainWindow extends JFrame implements ActionListener{
	private JFrame jframe = new JFrame("SFIS");
	private JPanel panel;
	private JPanel searchPanel;
	private JLabel titleLabel;
	
	private JTextField search;
	private JList<String> list;
	private JButton addButton, searchButton,incButton,decButton;
	private DefaultListModel<String> fridgeList;
	private Fridge inv;
	private List<StoredItem> displayItems;
	
	private JPanel viewPanel;
	private JPanel compressedView;
	private ExpressiveView expressiveView;
	private JButton viewToggler;
	
	private ImageIcon compressedIcon;
	private ImageIcon expressiveIcon;
	
	public mainWindow() {
	    // create our jframe as usual
		List<StoredItem> items = DBProxy.getInstance().loadItems();
		inv = new Fridge(items);
			
		panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(25,40,40,40));
	    panel.setBackground(Color.black);
		jframe.add(panel,BorderLayout.NORTH);
	
	    
	    titleLabel = new JLabel("Smart Fridge Tracker");
	    titleLabel.setForeground(Color.white);
	    titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
	    panel.add(titleLabel);
	    //search and list panel
	    searchPanel = new JPanel();
	    searchPanel.setBackground(Color.black);
	    searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
	    searchPanel.setBounds(0,100,1000,500);
	    jframe.add(searchPanel);
	    
	    JPanel topPanel = new JPanel();
	    topPanel.setBackground(Color.black);
	    searchPanel.add(topPanel);
	    
	    search = new JTextField();
	    search.setFont(new Font("Arial", Font.PLAIN, 16));
	    search.setBackground(Color.gray);
	    search.setBounds(0,100,300,500);
	    search.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	    search.setPreferredSize(new Dimension(300,50));
	    topPanel.add(search);
	    
	    
	    searchButton = new JButton("Search");
	    searchButton.addActionListener(this);
	    searchButton.setPreferredSize(new Dimension(100,50));
	    topPanel.add(searchButton);
	    
	    
	    addButton = new JButton("+");
	    addButton.addActionListener(this);
	    addButton.setPreferredSize(new Dimension(50,50));
	    topPanel.add(addButton);
	    
	    compressedIcon = new ImageIcon("resources/CompressedViewIcon.png");
	    compressedIcon = new ImageIcon(compressedIcon.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));
	    expressiveIcon = new ImageIcon("resources/ExpressiveViewIcon.png");
	    expressiveIcon = new ImageIcon(expressiveIcon.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));
	    viewToggler = new JButton();
	    viewToggler.setPreferredSize(new Dimension(50, 50));
	    viewToggler.setIcon(expressiveIcon);
	    viewToggler.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	    viewToggler.addActionListener(this);
	    topPanel.add(viewToggler);
	    
	    displayItems = inv.getFridgeItems();
	    list = new JList<String>();
	    list.setBackground(Color.gray);
	    list.setFont(new Font("Arial", Font.BOLD, 24));
	    list.setPreferredSize(new Dimension(800, 220));
	    list.setBounds(0,300,1000,500);
	    
	    JScrollPane compressedList = new JScrollPane(list);
	    compressedList.setPreferredSize(new Dimension(820, 220));
	    compressedView = new JPanel();
	    compressedView.setBackground(Color.black);
	    compressedView.setPreferredSize(new Dimension(820, 400));
	    compressedView.add(compressedList);
	    
	    viewPanel = new JPanel();
	    viewPanel.setBackground(Color.black);
	    viewPanel.add(compressedView);
	    
	    expressiveView = new ExpressiveView(inv);
	    expressiveView.setPreferredSize(new Dimension(820, 400));
	    
	    searchPanel.add(viewPanel);
	    
	    this.generateList();
	    
	    incButton = new JButton("Increment");
	    incButton.addActionListener(this);
	    incButton.setPreferredSize(new Dimension(200,50));
	    compressedView.add(incButton);
	    
	    decButton = new JButton("Decrement");
	    decButton.addActionListener(this);
	    decButton.setPreferredSize(new Dimension(200,50));
	    compressedView.add(decButton);
	    
	    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    jframe.getContentPane().setBackground(Color.black);
	    // set the jframe size and location, and make it visible
	    jframe.setPreferredSize(new Dimension(1000, 650));
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
		else if (e.getSource() == searchButton) {		
			mainSearchHandler();
		}
		else if (e.getSource() == incButton) {	
			int itemIndex = list.getSelectedIndex();
			this.displayItems.get(itemIndex).executeIncrement();
			this.fridgeList.set(itemIndex, this.displayItems.get(itemIndex).getDescription());
			this.list.revalidate();
			
			DBProxy.getInstance().updateFridge(inv);
			
		}
		else if (e.getSource() == decButton) {	
			int itemIndex = list.getSelectedIndex();
			if (this.displayItems.get(itemIndex).getStockableItem().getStock() < 1) {
				JOptionPane.showMessageDialog(null, "Item is at 0 stock", "Notice", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			this.displayItems.get(itemIndex).executeDecrement();
			this.fridgeList.set(itemIndex, this.displayItems.get(itemIndex).getDescription());
			this.list.revalidate();
		}
		else if (e.getSource() == viewToggler) {
			if (viewToggler.getIcon() == compressedIcon) {
				viewToggler.setIcon(expressiveIcon);
				
				this.displayItems = inv.getFridgeItems();
				this.generateList();
				
				viewPanel.remove(expressiveView);
				viewPanel.add(compressedView);
				viewPanel.revalidate();
				viewPanel.repaint();
				return;
			}
			
			viewToggler.setIcon(compressedIcon);
			
			this.displayItems = inv.getFridgeItems();
			expressiveView.generateList(displayItems);
			
			viewPanel.remove(compressedView);
			viewPanel.add(expressiveView);
			viewPanel.revalidate();
			viewPanel.repaint();
		}
	}
	
	
	public void mainSearchHandler() {
		//We want to ensure we can search our JList, and let it return to its former state if the search is cleared.
		//We do this by passing our inventory and using .contains to add eveyrthing that matches to our JList 
		
		String searchString = search.getText();
		this.displayItems = inv.search(searchString);
		this.generateList();
	}
	
	public void generateList() {
		DefaultListModel<String> updatedFridgeList = new DefaultListModel<String>();
		for (StoredItem item: this.displayItems) {
			updatedFridgeList.addElement(item.getDescription());
		}
		 
		this.fridgeList = updatedFridgeList;
		this.list.setModel(updatedFridgeList);
		this.list.setPreferredSize(new Dimension(800, 30 * fridgeList.size()));
		this.list.revalidate();
		 
		this.compressedView.revalidate();
	}
	
	public void addNewItem() {
		int itemIndex = inv.getFridgeItems().size() - 1;
		displayItems.add(inv.getFridgeItems().get(itemIndex));
	    fridgeList.addElement(inv.getFridgeItems().get(itemIndex).getDescription());
	    
	    list.setModel(fridgeList);
	    list.setPreferredSize(new Dimension(800, 30 * fridgeList.size()));
	    list.revalidate();
	    
	    expressiveView.addItem(inv.getFridgeItems().get(itemIndex));
	}
}
