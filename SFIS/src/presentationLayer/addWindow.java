package presentationLayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import domainLayer.DBProxy;

public class addWindow extends JFrame {
	private JPanel addMethodPanel;
	
	public addWindow(DBProxy db, ActionListener listener) {		
		BoxLayout overallLayout = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);
		this.getContentPane().setLayout(overallLayout);
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	    panel.setBackground(Color.black);
	    this.add(panel);
	    
	    JLabel titleLabel = new JLabel("Add New Item");
	    titleLabel.setForeground(Color.white);
	    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
	    panel.add(titleLabel);
	    panel.setMaximumSize(new Dimension(panel.getMaximumSize().width, panel.getPreferredSize().height));
	    
	    JPanel selectionPanel = new JPanel();
	    BoxLayout selectionLayout = new BoxLayout(selectionPanel, BoxLayout.Y_AXIS);
	    selectionPanel.setLayout(selectionLayout);
	    selectionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    selectionPanel.setBackground(Color.black);
	    this.add(selectionPanel);
	    
	    JRadioButton fromDbOption = new JRadioButton("Select an item from our database.");
	    fromDbOption.setName("databaseSelect");
	    JRadioButton manualItemOption = new JRadioButton("Create your own item.");
	    manualItemOption.setName("manualSelect");
	    
	    ButtonGroup grp = new ButtonGroup();
	    grp.add(fromDbOption);
	    grp.add(manualItemOption);
	    
	    selectionPanel.add(fromDbOption);
	    selectionPanel.add(manualItemOption);
	    
	    fromDbOption.addActionListener(listener);
	    manualItemOption.addActionListener(listener);
	    
	    JPanel addMethodPanel = new JPanel();
		addMethodPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	    addMethodPanel.setBackground(Color.black);
	    this.add(addMethodPanel);
	    this.addMethodPanel = addMethodPanel;
	    
	    this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	    this.getContentPane().setBackground(Color.black);
	    // set the jframe size and location, and make it visible
	    this.setPreferredSize(new Dimension(1000, 600));
	    this.pack();
	    this.setLocationRelativeTo(null);
	    this.setVisible(true);
		
	}
	
	public void setAddingMethod(JPanel method) {
		
		if (this.addMethodPanel.getComponentCount() == 1) {
			this.addMethodPanel.remove(this.addMethodPanel.getComponent(0));
		}
		
		this.addMethodPanel.add(method);
		this.addMethodPanel.revalidate();
		this.repaint();
	}

}
