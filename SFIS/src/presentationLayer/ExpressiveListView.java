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
import domainLayer.StoredItem;

public class ExpressiveListView extends JPanel implements ListView{
	
	Fridge inv;
	JPanel listView;
	JScrollPane scroll;
	
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
	
	public ExpressiveListView(Fridge inv) {
		this.inv = inv;
		
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(820, 400));
		
		scroll = new JScrollPane();
		this.add(scroll);
		
		this.generateList(inv.getItems());
	}
	
	public void generateList(List<StoredItem> items) {
		if (listView != null) 
			this.remove(scroll);
			
		listView = new JPanel();
		listView.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		listView.setBackground(new Color(20, 20, 20));
		listView.setLayout(new BoxLayout(listView, BoxLayout.Y_AXIS));
		
		for (StoredItem item: items) {
			ExpressiveItemComponent itemView = new ExpressiveItemComponent(item, this);
			listView.add(itemView);
		}
		
		scroll = new JScrollPane(listView);
		scroll.setViewportBorder(BorderFactory.createEmptyBorder(10, 2, 15, 2));
		scroll.setBackground(listView.getBackground());
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

	public void updateList(StoredItem itemObj) {
		inv.updateItem(itemObj);		
	}
	
	
}
