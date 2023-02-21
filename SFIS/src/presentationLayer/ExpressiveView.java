package presentationLayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import domainLayer.DBProxy;
import domainLayer.Fridge;
import domainLayer.StoredItem;

public class ExpressiveView extends JPanel {
	
	Fridge inv;
	JPanel listView;
	JScrollPane scroll;
	
	public static void main(String[] args) {
		JFrame jframe = new JFrame("Hi");
		jframe.add(new ExpressiveView(new Fridge(DBProxy.getInstance().loadItems())));
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    jframe.getContentPane().setBackground(Color.black);
	    jframe.setPreferredSize(new Dimension(600, 600));
	    jframe.pack();
	    jframe.setLocationRelativeTo(null);
	    jframe.setVisible(true);
	}
	
	public ExpressiveView(Fridge inv) {
		this.inv = inv;
		
		this.setLayout(new BorderLayout());
		
		scroll = new JScrollPane();
		this.add(scroll);
		
		this.generateList(inv.getFridgeItems());
	}
	
	public void generateList(List<StoredItem> items) {
		if (listView != null) 
			this.remove(scroll);
			
		listView = new JPanel();
		listView.setLayout(new BoxLayout(listView, BoxLayout.Y_AXIS));
		
		for (StoredItem item: items) {
			ExpressiveItemComponent itemView = new ExpressiveItemComponent(item, this);
			listView.add(itemView);
		}
		
		scroll = new JScrollPane(listView);
		this.add(scroll);
		this.revalidate();
	}
	
	public void addItem(StoredItem item) {
		ExpressiveItemComponent itemView = new ExpressiveItemComponent(item, this);
		listView.add(itemView);
		
		listView.revalidate();
	}
	
	public void removeItem(ExpressiveItemComponent item) {
		inv.remove(item.getItemObj());
		
		listView.remove(item);
		listView.repaint();
		listView.revalidate();
	}
	
	
}