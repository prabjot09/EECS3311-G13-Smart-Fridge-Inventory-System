package presentationLayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JRadioButton;

import domainLayer.DBProxy;
import domainLayer.Fridge;

public class AddWindowController implements ActionListener{
	private addWindow addWindowView;
	private mainWindow homeView;
	private Fridge fridge;
	
	private ActionListener addMethodController;
	
	public AddWindowController(mainWindow homeView) {
		this.addWindowView = new addWindow(this);
		this.homeView = homeView;
		this.fridge = homeView.getFridge();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			JRadioButton button = (JRadioButton) e.getSource();
			if (button.getName() == "databaseSelect") {
				this.addMethodController = new AddSelectController(addWindowView, homeView, this.fridge);
			}
			else if (button.getName() == "manualSelect") {
				this.addMethodController = new AddCreateController(addWindowView, homeView, this.fridge);
			}
		}
		catch (Exception exception) {
			System.out.println(exception.getStackTrace());
		}
		
		
	}

}
