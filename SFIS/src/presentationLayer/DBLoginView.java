package presentationLayer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import appLayer.App;
import domainLayer.DBProxy;
import presentationLayer.swingExtensions.CustomBoxPanel;
import presentationLayer.swingExtensions.CustomButton;
import presentationLayer.swingExtensions.CustomPanel;
import presentationLayer.swingExtensions.TextInput;

public class DBLoginView extends JFrame implements ActionListener {
	
	private JButton loginButton;
	private TextInput userField;
	private TextInput passField;
	
	public static void main(String[] args) {
		new DBLoginView();
	}
	
	public DBLoginView() {
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.setTitle("DB Login");
		
		JPanel titlePanel = new CustomPanel(Color.black, null);
		titlePanel.setBorder(BorderFactory.createEmptyBorder(35, 10, 10, 10));
	    this.add(titlePanel);
	    
	    JLabel titleLabel = new JLabel("Database Access Login");
	    titleLabel.setForeground(Color.white);
	    titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
	    titlePanel.add(titleLabel);
	    
	    JPanel inputPanel = new CustomBoxPanel(Color.black, BoxLayout.Y_AXIS, 0);
	    inputPanel.setMaximumSize(new Dimension(300, 300));
		this.add(inputPanel);
		
		JPanel userInput = new CustomBoxPanel(Color.black, BoxLayout.X_AXIS, 20);
		inputPanel.add(userInput);
		
		JLabel userLabel = new JLabel("Username: ");
	    userLabel.setForeground(Color.white);
	    userLabel.setFont(new Font("Arial", Font.BOLD, 16));
	    userInput.add(userLabel);
	    
	    userField = new TextInput("");
	    userField.setFont(new Font("Arial", Font.PLAIN, 16));
		userField.setBackground(Color.gray);
		//userField.setBounds(0,100,300,500);
	    userField.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		//userField.setPreferredSize(new Dimension(300,50));
		userInput.add(userField);

		JPanel passInput = new CustomBoxPanel(Color.black, BoxLayout.X_AXIS, 20);
		inputPanel.add(passInput);
		
		JLabel passLabel = new JLabel("Password: ");
		passLabel.setForeground(Color.white);
		passLabel.setFont(new Font("Arial", Font.BOLD, 16));
	    passInput.add(passLabel);
	    
	    passField = new TextInput("");
	    passField.setFont(new Font("Arial", Font.PLAIN, 16));
	    passField.setBackground(Color.gray);
	    //passField.setBounds(0,100,300,500);
		passField.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		//passField.setPreferredSize(new Dimension(300,50));
		passInput.add(passField);
		
		JPanel buttonPanel = new CustomPanel(Color.black, null, 20);
		this.add(buttonPanel);
		
		loginButton = new CustomButton("Login", this, 20);
		buttonPanel.add(loginButton);
		
	    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    this.addWindowListener(new WindowAdapter() {
	    	@Override
	    	public void windowClosing(WindowEvent e) {
	    		// add later
	    	}
	    });
	    this.getContentPane().setBackground(Color.black);
	    this.setPreferredSize(new Dimension(500, 420));
	    this.pack();
	    this.setLocationRelativeTo(null);
	    this.setVisible(true);
	}
	
	public boolean validateInput() {
		if (userField.getInput().equals("")) {
			JOptionPane.showMessageDialog(this, "Please enter a username.", "Warning", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		else if (passField.getInput().equals("")) {
			JOptionPane.showMessageDialog(this, "Please enter a password.", "Warning", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!validateInput()) {
			return;
		}
		
		App.getInstance().initializeApplication(userField.getInput(), passField.getInput());		
	}

	public void loginFail() {
		userField.clearField();
		passField.clearField();
		
		JOptionPane.showMessageDialog(this, "Wrong user or password for SQL Database.", "Error", JOptionPane.ERROR_MESSAGE);
	}
}
