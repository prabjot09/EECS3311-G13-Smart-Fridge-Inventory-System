package presentationLayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import domainLayer.DiscreteStockableItem;
import domainLayer.FoodItem;
import domainLayer.FridgeItem;
import domainLayer.StockableItem;
import domainLayer.StoredItem;

public class ExpressiveItemComponent extends JPanel implements ActionListener{
	private JLabel name;
	private JLabel quantity;
	private JProgressBar quantityVisual;
	private StoredItem itemObj;
	
	private JButton incButton;
	private JButton decButton;
	private JButton delButton;
	
	private ExpressiveListView view;
	
	public static void main(String[] args) {
		JPanel lay = new JPanel();
		lay.setLayout(new BoxLayout(lay, BoxLayout.Y_AXIS));
		
		for (int i = 0; i < 6; i++) {
			FridgeItem item = new FridgeItem();
			FoodItem fItem = new FoodItem();
			fItem.setName("a - " + i);
			item.setFoodItem(fItem);
			item.setStockableItem(new DiscreteStockableItem(3));
			item.getStockableItem().setMax(4);
			ExpressiveItemComponent a = new ExpressiveItemComponent(item, null);
			
			lay.add(a);			
		}
		
		JFrame jframe = new JFrame("Hi");
		jframe.add(lay);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    jframe.getContentPane().setBackground(Color.black);
	    jframe.setPreferredSize(new Dimension(600, 600));
	    jframe.pack();
	    jframe.setLocationRelativeTo(null);
	    jframe.setVisible(true);
	    
	    
	}
	
	public ExpressiveItemComponent (StoredItem itemObj, ExpressiveListView view) {	
		this.itemObj = itemObj;
		this.view = view;
		
		
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(50, 50, 50)));
	    this.setBackground(new Color(20, 20, 20));
	    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	    
	    JPanel upperPanel = new JPanel();
	    upperPanel.setLayout(new BorderLayout());
	    upperPanel.setBackground(this.getBackground());
	    upperPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 0));
	    this.add(upperPanel);
	    
	    JPanel infoPanel = new JPanel();
	    infoPanel.setBackground(this.getBackground());
	    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
	    upperPanel.add(infoPanel, BorderLayout.LINE_START);
	    
	    name = new JLabel("Name: " + itemObj.getFoodItem().getName());
	    name.setForeground(Color.white);
	    name.setFont(new Font("Arial", Font.BOLD, 18));
	    infoPanel.add(name);
	    
	    quantity = new JLabel("Quantity: " + itemObj.getStockableItem().getDescription());
	    quantity.setForeground(Color.white);
	    quantity.setFont(new Font("Arial", Font.BOLD, 18));
	    infoPanel.add(quantity);
	    
	    delButton = new JButton("Remove");
	    delButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    delButton.addActionListener(this);
	    upperPanel.add(delButton, BorderLayout.LINE_END);
	    
	    JPanel quantityPanel = new JPanel();
	    quantityPanel.setBackground(this.getBackground());
	    quantityPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
	    quantityPanel.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    this.add(quantityPanel);
	    	    
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.weightx = 1.0;
	    quantityVisual = new JProgressBar(SwingConstants.HORIZONTAL);
	    quantityVisual.setStringPainted(true);
	    quantityVisual.setForeground(Color.BLUE);
	    StockableItem stock = this.itemObj.getStockableItem();
	    int percentQuantity = stock.calculatePercent();
	    quantityVisual.setValue(percentQuantity);
	    quantityPanel.add(quantityVisual, c);
	    
	    c.weightx = 0.06;
	    c.weighty = 1.0;
	    c.insets = new Insets(0, 12, 0, 0);
	    incButton = new JButton("+");
	    incButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	    incButton.addActionListener(this);
	    quantityPanel.add(incButton, c);
	    
	    decButton = new JButton("-");
	    decButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	    decButton.addActionListener(this);
	    quantityPanel.add(decButton, c);	    
	}
	
	public void updateLabel() {
		name.setText("Name: " + itemObj.getFoodItem().getName());
		quantity.setText("Quantity: " + itemObj.getStockableItem().getDescription());
		
		StockableItem stock = this.itemObj.getStockableItem();
	    int percentQuantity = stock.calculatePercent();
	    quantityVisual.setValue(percentQuantity);
		
		this.revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton) e.getSource();
		
		if (clicked == incButton) {
			this.itemObj.executeIncrement();
			view.updateList(itemObj);
			this.updateLabel();
		}
		else if (clicked == decButton) {
			this.itemObj.executeDecrement();
			view.updateList(itemObj);
			this.updateLabel();
		}
		else if (clicked == delButton) {
			view.removeItem(this);
		}
		
	}
	
	public StoredItem getItemObj() {
		return this.itemObj;
	}
}
