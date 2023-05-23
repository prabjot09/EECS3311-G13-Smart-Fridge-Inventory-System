package presentationLayer.itemListView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicProgressBarUI;

import appLayer.App;
import domainLayer.DiscreteStockableItem;
import domainLayer.FoodItem;
import domainLayer.FridgeItem;
import domainLayer.Pair;
import domainLayer.StockableItem;
import domainLayer.StoredItem;
import presentationLayer.swingExtensions.CustomBoxPanel;
import presentationLayer.swingExtensions.CustomButton;
import presentationLayer.swingExtensions.CustomPanel;
import presentationLayer.swingExtensions.GridConstraintsSpec;

public class ExpressiveItemComponent extends JPanel implements ActionListener{
	private JLabel name, quantity, expiry;
	private JProgressBar quantityVisual;
	private StoredItem itemObj;
	
	private JButton incButton, decButton, delButton, groceryListButton;
	private JPanel rightPanel;
	
	private ExpressiveListView view;
	
	private static List<Pair<Pair<Integer, Integer>, Color>> barColors = Arrays.asList(
		new Pair<>(new Pair<>(67, 100), new Color(33, 237, 84)),
		new Pair<>(new Pair<>(34, 66), new Color(237, 196, 26)),
		new Pair<>(new Pair<>(0, 33), new Color(247, 23, 26))
	);
	
	public static void main(String[] args) {
		JPanel lay = new JPanel();
		lay.setLayout(new BoxLayout(lay, BoxLayout.Y_AXIS));
		
		for (int i = 0; i < 6; i++) {
			FridgeItem item = new FridgeItem();
			FoodItem fItem = new FoodItem();
			fItem.setName("a - " + i);
			item.setFoodItem(fItem);
			item.setStockableItem(new DiscreteStockableItem(1));
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
		
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(80, 80, 80)));
	    this.setBackground(new Color(40, 40, 40));
	    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	    
	    itemInfoBuilder();
	    itemQuantityControlBuilder();	    
	}
	
	
	public void itemInfoBuilder() {
		JPanel upperPanel = new CustomPanel(this.getBackground(), new BorderLayout());
	    upperPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 0));
	    this.add(upperPanel);
	    
	    JPanel infoPanel = new CustomBoxPanel(this.getBackground(), BoxLayout.Y_AXIS);
	    upperPanel.add(infoPanel, BorderLayout.LINE_START);
	    
	    name = new JLabel("Name: " + itemObj.getFoodItem().getName());
	    name.setForeground(Color.white);
	    name.setFont(new Font("Arial", Font.BOLD, 16));
	    infoPanel.add(name);
	    
	    quantity = new JLabel("Quantity: " + itemObj.getStockableItem().getDescription());
	    quantity.setForeground(Color.white);
	    quantity.setFont(new Font("Arial", Font.BOLD, 16));
	    infoPanel.add(quantity);
	    
	    try {
	    	FridgeItem item = (FridgeItem) itemObj;
	    	expiry = new JLabel("Best Before: " + item.getExpDate().toString());
	    	expiry.setForeground(Color.white);
	    	expiry.setFont(new Font("Arial", Font.BOLD, 16));
	    	infoPanel.add(expiry);
	    	
	    	this.updateLabel();
	    } catch (Exception e) {
	    	
	    }
	    
	    rightPanel = new CustomPanel(this.getBackground(), new GridBagLayout());
	    upperPanel.add(rightPanel, BorderLayout.LINE_END);
	    
	    delButton = new CustomButton("Remove", this, 10);
	    rightPanel.add(delButton, GridConstraintsSpec.coordinateConstraints(0, 0));
	    
	    rightPanel.add(Box.createRigidArea(new Dimension(8, 8)), GridConstraintsSpec.coordinateConstraints(1, 0));
	    
	    groceryListButton = new CustomButton("Add to Grocery List", this, 10);
		rightPanel.add(groceryListButton, GridConstraintsSpec.coordinateConstraints(2, 0));	
	    	    
	}
	
	
	
	public void itemQuantityControlBuilder() {
		JPanel quantityPanel = new CustomPanel(this.getBackground(), new GridBagLayout());
	    quantityPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
	    GridBagConstraints c = new GridBagConstraints();
	    this.add(quantityPanel);
	    	    
	    quantityVisual = new JProgressBar(SwingConstants.HORIZONTAL);
	    quantityVisual.setStringPainted(true);
	    quantityVisual.setUI(new BasicProgressBarUI() {
	        protected Color getSelectionBackground() { return Color.gray; }
	        protected Color getSelectionForeground() { return Color.gray; }
	      });
	    StockableItem stock = this.itemObj.getStockableItem();
	    int percentQuantity = stock.calculatePercent();
	    quantityVisual.setValue(percentQuantity);
	    c = GridConstraintsSpec.stretchableFillConstraints(0, 0, 1, 0, GridBagConstraints.HORIZONTAL);
	    quantityPanel.add(quantityVisual, c);
	    
	    incButton = new CustomButton("+", this, 5);
	    c = GridConstraintsSpec.stretchableFillConstraints(1, 0, 0.06, 1, GridBagConstraints.HORIZONTAL);
	    c.insets = new Insets(0, 12, 0, 0);
	    quantityPanel.add(incButton, c);
	    
	    decButton = new CustomButton("-", this, 5);
	    c = GridConstraintsSpec.stretchableFillConstraints(2, 0, 0.06, 1, GridBagConstraints.HORIZONTAL);
	    c.insets = new Insets(0, 12, 0, 0);
	    quantityPanel.add(decButton, c);
	    
	    colorProgressBar();
	}
	
	
	
	public void updateLabel() {
		name.setText("Name: " + itemObj.getFoodItem().getName());
		quantity.setText("Quantity: " + itemObj.getStockableItem().getDescription());
		if (expiry != null) {
			FridgeItem item = (FridgeItem) itemObj;
			expiry.setText("Best Before: " + item.getExpDate().toString());
			if (item.isExpiring()) {
				expiry.setText(expiry.getText() + " [EXPIRY WARNING]");
				name.setForeground(Color.red);
				quantity.setForeground(Color.red);
				expiry.setForeground(Color.red);
			}
		}
		
		StockableItem stock = this.itemObj.getStockableItem();
	    int percentQuantity = stock.calculatePercent();
	    
	    quantityVisual.setValue(percentQuantity);
	    colorProgressBar();
		
		this.revalidate();
	}
	
	
	public void colorProgressBar() {
		int amt = quantityVisual.getValue();
		
		for(Pair<Pair<Integer, Integer>, Color> entry: barColors) {
			Pair<Integer, Integer> range = entry.getA();
			if (amt >= range.getA() && amt <= range.getB()) {
				quantityVisual.setForeground(entry.getB());
				break;
			}	
		}
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
			
			StockableItem stock = this.itemObj.getStockableItem();
		    int percentQuantity = stock.calculatePercent();
		    quantityVisual.setValue(percentQuantity);
			
			int itemStockPercent = this.itemObj.getStockableItem().calculatePercent();
			int groceryThreshold = App.getInstance().getSettings().getAddGroceryListThreshold();
			
			//condition. stock lower than threshold and item is not in grocery list
			if ( (itemStockPercent < groceryThreshold) && (App.getInstance().getGroceryList().itemIndex(this.itemObj) == -1) ) {
				view.groceryVisualAdd(this.itemObj);
			}
		}
		else if (clicked == delButton) {
			view.removeItem(this);
		}
		else if (clicked == groceryListButton) {
			view.groceryVisualAdd(this.getItemObj());
		}
	}
	
	public StoredItem getItemObj() {
		return this.itemObj;
	}

	public void setStockChangeMode(boolean incrementEnabled, boolean decrementEnabled) {
		incButton.setEnabled(incrementEnabled);
		decButton.setEnabled(decrementEnabled);		
	}
	
	public void removeGroceryLink() {
		rightPanel.remove(groceryListButton);
		rightPanel.remove(rightPanel.getComponentCount()-1);
	}
}
