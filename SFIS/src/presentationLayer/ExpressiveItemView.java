package presentationLayer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domainLayer.StoredItem;

public class ExpressiveItemView extends JPanel implements ActionListener{
	private JLabel itemLabel;
	private StoredItem itemObj;
	
	public ExpressiveItemView (StoredItem itemObj) {		
		this.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
	    this.setBackground(Color.GRAY);
	    
	    this.itemLabel = new JLabel(itemObj.getFoodItem().getName() + ": " + 
	    							  itemObj.getStockableItem().getStock() + " Units");
	    this.itemLabel.setForeground(Color.white);
	    this.itemLabel.setFont(new Font("Arial", Font.BOLD, 24));
	    this.add(this.itemLabel);
	    this.itemObj = itemObj;
	    
	    JButton incButton = new JButton("+");
	    incButton.addActionListener(this);
	    incButton.setPreferredSize(new Dimension(45, 45));
	    this.add(incButton);
	    
	    JButton decButton = new JButton("-");
	    decButton.addActionListener(this);
	    decButton.setPreferredSize(new Dimension(45, 45));
	    this.add(decButton);
	}
	
	public void updateLabel() {
		this.itemLabel.setText(this.itemObj.getFoodItem().getName() + ": " + 
							   this.itemObj.getStockableItem().getStock() + " Units");
		this.itemLabel.revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton) e.getSource();
		
		if (clicked.getText().equals("+")) {
			this.itemObj.getStockableItem().increment(1);
			this.updateLabel();
		}
		else if (clicked.getText().equals("-")) {
			this.itemObj.getStockableItem().decrement(1);
			this.updateLabel();
		}
		
	}
}
