package presentationLayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import domainLayer.DBProxy;
import domainLayer.Fridge;

public class addWindow extends JFrame implements ActionListener{
	private JPanel addMethodPanel;
	private mainWindow homeView;
	private Fridge inv;
	
	private ActionListener addMethodController;
	
	//back button
	private JButton backButton;
	
	public addWindow(mainWindow homeView, Fridge inv) {	
		this.inv = inv;
		this.homeView = homeView;
		
		BoxLayout overallLayout = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);
		this.getContentPane().setLayout(overallLayout);
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	    panel.setBackground(Color.black);
	    this.add(panel);
	    
	    JLabel titleLabel = new JLabel("Add New Item");
	    titleLabel.setForeground(Color.white);
	    titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
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
	    
	    //back button
	    backButton = new JButton("Back");
	    backButton.addActionListener(this);
	    this.add(backButton);
	    
	    ButtonGroup grp = new ButtonGroup();
	    grp.add(fromDbOption);
	    grp.add(manualItemOption);
	    
	    selectionPanel.add(fromDbOption);
	    selectionPanel.add(manualItemOption);
	    
	    fromDbOption.addActionListener(this);
	    manualItemOption.addActionListener(this);
	    
	    JPanel addMethodPanel = new JPanel();
		addMethodPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	    addMethodPanel.setBackground(Color.black);
	    this.add(addMethodPanel);
	    this.addMethodPanel = addMethodPanel;
	    
	    //dispose on close while also opening mainwindow on close
	    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    this.addWindowListener(new WindowAdapter() {
	    	@Override
	    	public void windowClosing(WindowEvent e) {
	    		homeView.makeVisible();
	    	}
	    });
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

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			//Back button operation
			if (e.getSource() == backButton) {
				homeView.makeVisible();
				this.dispose();
				return;
			}
			JRadioButton button = (JRadioButton) e.getSource();
			if (button.getName() == "databaseSelect") {
				this.addMethodController = new AddSelectController(this, homeView, this.inv);
			}
			else if (button.getName() == "manualSelect") {
				this.addMethodController = new AddCreateController(this, homeView, this.inv);
			}
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}
