package presentationLayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JRadioButton;

import domainLayer.DBProxy;

public class AddWindowController implements ActionListener{
	addWindow addWindowView;
	mainWindow homeView;
	DBProxy database;
	ActionListener addSelectController;
	
	public AddWindowController(DBProxy database, mainWindow homeView) {
		this.addWindowView = new addWindow(database, this);
		this.database = database;
		this.homeView = homeView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			JRadioButton button = (JRadioButton) e.getSource();
			if (button.getName() == "databaseSelect") {
				this.addSelectController = new AddSelectController(database, addWindowView, homeView);
				System.out.println("Create db select");
			}
			else if (button.getName() == "manualSelect") {
				System.out.println("Create manual select");	
			}
		}
		catch (Exception exception) {
			
		}
		
		
	}

}
