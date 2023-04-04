package presentationLayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import appLayer.App;
import domainLayer.DBProxy;

public class AppWindow extends JFrame{
	private AppFrameView currentView;
	private List<AppFrameView> viewStack;
	private static AppWindow window;
	
	private AppWindow() {
		this.currentView = null;
		this.viewStack = new ArrayList<>();
	}
	
	
	public static AppWindow getWindow() {
		if (window == null) {
			window = new AppWindow();
		}
		return window;
	}
	
	
	public void openWindow() {
		this.setTitle("SFIS");
		this.getContentPane().setLayout(new BorderLayout());
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.addWindowListener(new WindowAdapter() {
	    	@Override
	    	public void windowClosing(WindowEvent e) {
	    		saveViews();
	    	}
	    });
		    
	    this.getContentPane().setBackground(Color.black);
	    this.setPreferredSize(new Dimension(1140, 650));
	    this.pack();
	    this.setLocationRelativeTo(null);
	    this.setVisible(true);
	}
	
	
	public void loadPreviousWindow() {
		if (viewStack.size() < 2) {
			return;
		}
		this.remove(currentView);
		
		viewStack.remove(viewStack.size() - 1);
		currentView = viewStack.get(viewStack.size() - 1);
		this.add(currentView);
		
		this.repaint();
		this.revalidate();
	}
	
	
	public void loadNewView(AppFrameView view) {
		if (currentView != null) {
			this.remove(currentView);
		}
		viewStack.add(view);
		currentView = view;
		
		this.add(view);
		this.repaint();
		this.revalidate();
	}
	
	
	public void saveViews() {
		for (AppFrameView frame: viewStack) {
			frame.saveData();
		}
	}
}
