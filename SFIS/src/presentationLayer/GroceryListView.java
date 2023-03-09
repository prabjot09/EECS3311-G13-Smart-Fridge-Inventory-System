package presentationLayer;

import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import domainLayer.*;

public class GroceryListView extends JPanel implements ActionListener {
	
	//variables
	private JButton removeGroceryButton, exportButton;
	private JComboBox<String> exportDecision;
	
	public GroceryListView() {
	    this.setBounds(0, 0, 500, 500);
	    this.setBackground(Color.WHITE);
	    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	    
	    //Panel for the title 
	    JPanel titlePanel = new JPanel();
	    titlePanel.setBackground(Color.BLACK);
	    titlePanel.setBorder(BorderFactory.createEmptyBorder(10,10,17,10));
	    this.add(titlePanel);
	    //Title 
	    JLabel groceryTitle = new JLabel("Your Grocery List");
	    groceryTitle.setForeground(Color.white);
	    groceryTitle.setFont(new Font("Arial", Font.PLAIN, 26));
	    titlePanel.add(groceryTitle);
	    
	    //Grocery viewing 
	    JList<String> viewList = new JList<String>();
	    viewList.setBackground(Color.GRAY);
	    JScrollPane scrollViewingPane = new JScrollPane(viewList);
	    scrollViewingPane.setBorder(BorderFactory.createLineBorder(Color.WHITE));
	    scrollViewingPane.setPreferredSize(new Dimension(400,280));
	    this.add(scrollViewingPane);
	    
	    //Panel for buttons
	    JPanel buttonsPanel = new JPanel();
	    buttonsPanel.setBackground(Color.BLACK);
	    buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	    buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
	    this.add(buttonsPanel);
	    //Remove grocery button
	    removeGroceryButton = new JButton("Remove Grocery");
	    removeGroceryButton.setPreferredSize(new Dimension(135,20));
	    removeGroceryButton.addActionListener(this);
	    buttonsPanel.add(removeGroceryButton);
	    //Export choices combo box
	    String exportChoices[] = {"Grocery List", "Favorites List"};
	    exportDecision = new JComboBox<String>(exportChoices);
	    exportDecision.setPreferredSize(new Dimension(110,20));
	    buttonsPanel.add(exportDecision);
	    //Export Button
	    exportButton = new JButton("Export");
	    exportButton.setPreferredSize(new Dimension(75,20));
	    exportButton.addActionListener(this);
	    buttonsPanel.add(exportButton);
	    
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == removeGroceryButton) {
			//
		}
		if (e.getSource() == exportButton) {
			if (exportDecision.getSelectedItem() == "Grocery List") {
				//
			}
			if (exportDecision.getSelectedItem() == "Export") {
				//
			}
		}
	}

}
