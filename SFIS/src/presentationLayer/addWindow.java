package presentationLayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import presentationLayer.swingExtensions.CustomBoxPanel;
import presentationLayer.swingExtensions.CustomButton;
import presentationLayer.swingExtensions.CustomPanel;

public class addWindow extends JPanel implements ActionListener{
	private JPanel addMethodPanel;
	private mainWindow homeView;
	
	private ActionListener addMethodController;
	
	//back button
	private JButton backButton;
	
	public addWindow(mainWindow homeView) {	
		this.homeView = homeView;
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panel = new CustomPanel(Color.black, 10);
	    this.add(panel);
	    
	    JLabel titleLabel = new JLabel("Add New Item");
	    titleLabel.setForeground(Color.white);
	    titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
	    panel.add(titleLabel);
	    panel.setMaximumSize(new Dimension(panel.getMaximumSize().width, panel.getPreferredSize().height));
	    
	    JPanel optionsPanel = new CustomPanel(Color.black, new FlowLayout(FlowLayout.CENTER));
	    this.add(optionsPanel);
	    
	    JPanel selectionPanel = new CustomBoxPanel(Color.black, BoxLayout.Y_AXIS, 10);
	    optionsPanel.add(selectionPanel);
	    
	    JRadioButton fromDbOption = new JRadioButton("Select an item from our database.");
	    fromDbOption.setName("databaseSelect");
	    JRadioButton manualItemOption = new JRadioButton("Create your own item.");
	    manualItemOption.setName("manualSelect");
	    
	    ButtonGroup grp = new ButtonGroup();
	    grp.add(fromDbOption);
	    grp.add(manualItemOption);
	    
	    selectionPanel.add(fromDbOption);
	    selectionPanel.add(manualItemOption);
	    
	    backButton = new CustomButton("Back", this);
	    selectionPanel.add(backButton);
	    
	    fromDbOption.addActionListener(this);
	    manualItemOption.addActionListener(this);
	    
	    JPanel addMethodPanel = new CustomPanel(Color.black, 10);
	    this.add(addMethodPanel);
	    this.addMethodPanel = addMethodPanel;
	    
	    //dispose on close while also opening mainwindow on close
//	    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//	    this.addWindowListener(new WindowAdapter() {
//	    	@Override
//	    	public void windowClosing(WindowEvent e) {
//	    		homeView.makeVisible();
//	    	}
//	    });
//	    this.getContentPane().setBackground(Color.black);
//	    // set the jframe size and location, and make it visible
//	    this.setPreferredSize(new Dimension(1000, 650));
//	    this.pack();
//	    this.setLocationRelativeTo(null);
//	    this.setVisible(true);
		
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
				// homeView.makeVisible();
				// this.dispose();
				AppWindow.getWindow().loadPreviousWindow();
				return;
			}
			JRadioButton button = (JRadioButton) e.getSource();
			if (button.getName() == "databaseSelect") {
				this.addMethodController = new AddSelectController(this, homeView);
			}
			else if (button.getName() == "manualSelect") {
				this.addMethodController = new AddCreateController(this, homeView);
			}
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}
