package presentationLayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domainLayer.FridgeItem;
import domainLayer.Pair;
import domainLayer.StoredItem;
import presentationLayer.swingExtensions.CustomBoxPanel;
import presentationLayer.swingExtensions.CustomButton;
import presentationLayer.swingExtensions.CustomPanel;

public class ButtonEmbeddedItemComponent extends JPanel implements ActionListener {

	private StoredItem item;
	private ButtonEmbeddedListView list;
	
	private JButton incButton, decButton, delButton, groceryButton;
	private List<Pair<JButton, Boolean>> buttonOn;
	
	private JPanel buttonPanel;
	private JLabel itemText;
		
	public ButtonEmbeddedItemComponent(ButtonEmbeddedListView list, StoredItem item, Color bg) {
		this.item = item;
		this.list = list;
		
		this.setBackground(bg);
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		JPanel itemPanel = new CustomPanel(Color.black, new BorderLayout());
		itemPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		this.add(itemPanel);
		
		itemText = new JLabel(item.getShortDesc());
		itemText.setFont(new Font("Arial", Font.PLAIN, 16));
		itemText.setForeground(Color.white);
		try {
			if (((FridgeItem) item).isExpiring())
				itemText.setForeground(Color.red);
		} catch (Exception e) {
			
		}
		itemPanel.add(itemText, BorderLayout.LINE_START);
		
		buttonPanel = new CustomBoxPanel(itemPanel.getBackground(), BoxLayout.X_AXIS);
		itemPanel.add(buttonPanel, BorderLayout.LINE_END);
		
		incButton = new JButton();
		incButton.addActionListener(this);
		incButton.setBorder(BorderFactory.createEmptyBorder());
		incButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		decButton = new JButton();
		decButton.addActionListener(this);
		decButton.setBorder(BorderFactory.createEmptyBorder());
		decButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		delButton = new JButton();
		delButton.addActionListener(this);
		delButton.setBorder(BorderFactory.createEmptyBorder());
		delButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		try {
			BufferedImage plusSign = ImageIO.read(new File("."+File.separator+"resources"+File.separator+"green_plus.png"));
			BufferedImage minusSign = ImageIO.read(new File("."+File.separator+"resources"+File.separator+"red_minus.png"));
			BufferedImage delSign = ImageIO.read(new File("."+File.separator+"resources"+File.separator+"blue_delete.png"));
			ImageIcon plusIcon = new ImageIcon(plusSign);
			ImageIcon minusIcon = new ImageIcon(minusSign);
			ImageIcon deleteIcon = new ImageIcon(delSign);
			Image scaledPlus = plusIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
			Image scaledMinus = minusIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
			Image scaledDelete = deleteIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
			incButton.setIcon(new ImageIcon(scaledPlus));
			decButton.setIcon(new ImageIcon(scaledMinus));
			delButton.setIcon(new ImageIcon(scaledDelete));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		groceryButton = new CustomButton("Add to Grocery", this, 7);
		groceryButton.setBackground(bg);
		groceryButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		groceryButton.setForeground(Color.white);
		
		buttonOn = new ArrayList<>(Arrays.asList(
				new Pair<>(incButton, true), 
				new Pair<>(decButton, true), 
				new Pair<>(delButton, true),
				new Pair<>(groceryButton, true)
			));
		generateButtonPanel();
	}
	
	public void generateButtonPanel() {
		int count = buttonPanel.getComponentCount();
		for (int i = 0; i < count; i++) {
			buttonPanel.remove(0);
		}
		
		for (int i = 0; i < buttonOn.size(); i++) {
			if (buttonOn.get(i).getB()) {
				buttonPanel.add(buttonOn.get(i).getA());
				buttonPanel.add(Box.createRigidArea(new Dimension(7, 7)));
			}
		}
		
		if (buttonPanel.getComponentCount() > 0)
			buttonPanel.remove(buttonPanel.getComponentCount()-1);
	
		buttonPanel.revalidate();
	}

	public void updateText() {
		itemText.setText(item.getShortDesc());
		itemText.setForeground(Color.white);
		try {
			if (((FridgeItem) item).isExpiring())
				itemText.setForeground(Color.red);
		} catch (Exception e) {
			
		}
		
		itemText.revalidate();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == incButton) {
			item.executeIncrement();
			list.updateItem(item);
			this.updateText();
		}
		else if (e.getSource() == decButton) {
			item.executeDecrement();
			list.updateItem(item);
			this.updateText();
		}
		else if (e.getSource() == delButton) {
			list.removeItem(item);
		}
		else if (e.getSource() == groceryButton) {
			list.addToGrocery(item);
		}
	}

	public void removeGroceryLink() {
		buttonOn.set(3, new Pair<>(groceryButton, false));
		generateButtonPanel();
	}

	public void setStockChangeMode(boolean increment, boolean decrement) {
		buttonOn.set(0, new Pair<>(incButton, increment));
		buttonOn.set(1, new Pair<>(decButton, decrement));
		generateButtonPanel();
	}
}
