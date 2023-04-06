package appLayer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import domainLayer.ApplicationClock;
import domainLayer.DBProxy;
import domainLayer.FavoritesList;
import domainLayer.Fridge;
import domainLayer.GroceryList;
import domainLayer.StoredItem;
import persistenceLayer.RealDB;
import persistenceLayer.StubDB;
import presentationLayer.AppWindow;
import presentationLayer.DBLoginView;
import presentationLayer.HomePageWindow;
import presentationLayer.mainWindow;
import presentationLayer.swingExtensions.CustomButton;

public class ChronoStubbedApp {	
	public static void main(String[] args) {
		App app = App.getInstance();
		
		DBProxy.getInstance().setDB(new StubDB());
		ApplicationClock.initSimulatedClock();
		
		app.loadData();		
		
		AppWindow.getWindow().openWindow();
		AppWindow.getWindow().loadNewView(new HomePageWindow());
		
		
		JButton clockButton = new CustomButton();
		clockButton.setText("Increment Clock");
		clockButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ApplicationClock.incrementClock();
			}
		});
		clockButton.setPreferredSize(new Dimension(200, 100));
		
		JFrame jframe = new JFrame("Clock");
		jframe.add(clockButton);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    jframe.getContentPane().setBackground(Color.black);
	    jframe.pack();
	    jframe.setLocationRelativeTo(null);
	    jframe.setVisible(true);
	    jframe.setLocation(0, 0);
	}
}
