package presentationLayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JRadioButton;

import domainLayer.DBProxy;

public class AddWindowController implements ActionListener{
	private addWindow addWindowView;
	private mainWindow homeView;
	private DBProxy database;
	private ActionListener addMethodController;
	
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
				this.addMethodController = new AddSelectController(database, addWindowView, homeView);
			}
			else if (button.getName() == "manualSelect") {
				this.addMethodController = new AddCreateController(database, addWindowView, homeView);
			}
		}
		catch (Exception exception) {
			System.out.println(exception.getStackTrace());
		}
		
		
	}

}
