package presentationLayer.itemListView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import domainLayer.DBProxy;
import domainLayer.Fridge;
import domainLayer.ItemManager;
import domainLayer.StoredItem;
import presentationLayer.AppWindow;
import presentationLayer.GroceryListView;
import presentationLayer.swingExtensions.CustomBoxPanel;

public class ExpressiveListView extends JPanel implements ListView{
	
	private ItemManager inv;
	private JPanel listView;
	private List<ExpressiveItemComponent> itemUIList;
	private JScrollPane scroll;
	private GroceryListView groceryView;
	
	public static void main(String[] args) {
		JFrame jframe = new JFrame("Hi");
		
		jframe.add(new ExpressiveListView(new Fridge(DBProxy.getInstance().loadItems())));
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    jframe.getContentPane().setBackground(Color.black);
	    jframe.setPreferredSize(new Dimension(600, 600));
	    jframe.pack();
	    jframe.setLocationRelativeTo(null);
	    jframe.setVisible(true);
	}
	
	public ExpressiveListView(ItemManager inv) {
		this.inv = inv;
		this.itemUIList = new ArrayList<>();
		
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.setBackground(Color.black);
		//this.setPreferredSize(new Dimension(820, 400));
		
		scroll = new JScrollPane();
		this.add(scroll);
		
		this.generateList(inv.getItems());
	}
	
	public void generateList(List<StoredItem> items) {
		if (listView != null) 
			this.remove(scroll);
			
		itemUIList = new ArrayList<>();
		listView = new CustomBoxPanel(new Color(40, 40, 40), BoxLayout.Y_AXIS, -1);
		listView.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		
		for (StoredItem item: items) {
			ExpressiveItemComponent itemView = new ExpressiveItemComponent(item, this);
			
			itemUIList.add(itemView);
			listView.add(itemView);
		}
		
		scroll = new JScrollPane(listView);
		scroll.setViewportBorder(BorderFactory.createEmptyBorder(10, 2, 15, 2));
		scroll.setBackground(listView.getBackground());
		this.add(scroll);
		this.revalidate();
	}
	
	public void removeItem(ExpressiveItemComponent item) {
		int confirm = JOptionPane.showConfirmDialog(AppWindow.getWindow(), 
                "Are you sure you want to remove " + item.getItemObj().getFoodItem().getName(), 
                "Item Removal Confirmation", JOptionPane.YES_NO_OPTION);
		if (confirm != 0) {
			return;
		}
		
		inv.remove(item.getItemObj());
		itemUIList.remove(item);
		listView.remove(item);
		listView.repaint();
		listView.revalidate();
	}

	public void updateItem(StoredItem item) {
		inv.updateItem(item);
		
		int index = -1;
		for (int i = 0; i < itemUIList.size(); i++) {
			if (itemUIList.get(i).getItemObj().sameItemDescription(item))
				index = i;
		}
		
		if (index == -1) {
			new ArrayIndexOutOfBoundsException(-1).printStackTrace();
			return;
		}
		itemUIList.get(index).updateLabel();
	}
	
	public void setGrocery(GroceryListView grocery) {
		this.groceryView = grocery;
	}
	
	public void groceryVisualAdd(StoredItem item) {
		groceryView.visualAdd(item);
	}

	@Override
	public void setStockChangeMode(boolean increment, boolean decrement) {
		for (ExpressiveItemComponent component: itemUIList) {
			component.setStockChangeMode(increment, decrement);
		}
	}
	
	@Override
	public void removeGroceryLink() {
		for (ExpressiveItemComponent component: itemUIList) {
			component.removeGroceryLink();
		}
		this.repaint();
		this.revalidate();
	}
}
