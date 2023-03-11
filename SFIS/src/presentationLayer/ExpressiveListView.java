package presentationLayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import domainLayer.DBProxy;
import domainLayer.Fridge;
import domainLayer.ItemManager;
import domainLayer.StoredItem;
import presentationLayer.swingExtensions.CustomBoxPanel;

public class ExpressiveListView extends JPanel implements ListView{
	
	private ItemManager inv;
	private JPanel listView;
	private JScrollPane scroll;
	private GroceryListView groceryView;
	
	private boolean fridgeFlag;
	
	public static void main(String[] args) {
		JFrame jframe = new JFrame("Hi");
		jframe.add(new ExpressiveListView(new Fridge(DBProxy.getInstance().loadItems()), true));
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    jframe.getContentPane().setBackground(Color.black);
	    jframe.setPreferredSize(new Dimension(600, 600));
	    jframe.pack();
	    jframe.setLocationRelativeTo(null);
	    jframe.setVisible(true);
	}
	
	public ExpressiveListView(ItemManager inv, boolean fridgeFlag) {
		this.inv = inv;
		
		this.setLayout(new BorderLayout());
		//this.setPreferredSize(new Dimension(820, 400));
		
		scroll = new JScrollPane();
		this.add(scroll);
		
		this.fridgeFlag = fridgeFlag;
		this.generateList(inv.getItems());
	}
	
	public ExpressiveListView(ItemManager inv, boolean fridgeFlag, GroceryListView groceryView) {
		this(inv, fridgeFlag);
		this.groceryView = groceryView;
	}
	
	public void generateList(List<StoredItem> items) {
		if (listView != null) 
			this.remove(scroll);
			
		listView = new CustomBoxPanel(new Color(20, 20, 20), BoxLayout.Y_AXIS, -1);
		listView.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		
		for (StoredItem item: items) {
			ExpressiveItemComponent itemView = new ExpressiveItemComponent(item, this, fridgeFlag);
			listView.add(itemView);
		}
		
		scroll = new JScrollPane(listView);
		scroll.setViewportBorder(BorderFactory.createEmptyBorder(10, 2, 15, 2));
		scroll.setBackground(listView.getBackground());
		this.add(scroll);
		this.revalidate();
	}
	
	public void addItem(StoredItem item) {
		ExpressiveItemComponent itemView = new ExpressiveItemComponent(item, this, fridgeFlag);
		listView.add(itemView);
		
		listView.revalidate();
	}
	
	public void removeItem(ExpressiveItemComponent item) {
		inv.remove(item.getItemObj());
		
		listView.remove(item);
		listView.repaint();
		listView.revalidate();
	}

	public void updateList(StoredItem itemObj) {
		inv.updateItem(itemObj);		
	}

	public StoredItem retrieveObj(StoredItem itemObj) {
		return inv.getItems().get(inv.itemIndex(itemObj));
		
	}
	
	public void groceryVisualAdd(StoredItem item) {
		groceryView.visualAdd(item);
	}
}
