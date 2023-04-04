package presentationLayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import appLayer.App;
import presentationLayer.swingExtensions.CustomButton;
import presentationLayer.swingExtensions.CustomPanel;
import presentationLayer.swingExtensions.GridConstraintsSpec;

public class HomePageWindow extends JPanel implements ActionListener {
	
	private JButton toFridgeButton;
	private JButton toFavoritesButton;
	private JButton toRecipeButton;
	private JButton toHistoryButton;
	private JButton toSettingsButton;
	private JButton toExportButton;
	
	public HomePageWindow() {
		this.setBackground(Color.black);
		this.setLayout(new BorderLayout());
		
		JPanel pagePanel = new CustomPanel(Color.black, new GridBagLayout(), 5);
		this.add(pagePanel);
		
		JLabel titleLabel = new JLabel("SFIS Home Page");
	    titleLabel.setForeground(Color.white);
	    titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
	    
	    JPanel titlePanel = new CustomPanel(Color.black, new FlowLayout(FlowLayout.CENTER), 40);
	    titlePanel.add(titleLabel);
	    
	    pagePanel.add(titlePanel, GridConstraintsSpec.stretchableFillConstraints(0, 0, 1, 0, GridBagConstraints.HORIZONTAL));
	    
	    JPanel buttonPanel = new CustomPanel(Color.black, new GridBagLayout(), 10);
	    pagePanel.add(buttonPanel, GridConstraintsSpec.stretchableFillConstraints(0, 1, 1, 1, GridBagConstraints.BOTH));
	    
	    toFridgeButton = buildRedirectButton("Your Fridge and Grocieries", buttonPanel, 0, 0);
	    toFavoritesButton = buildRedirectButton("Favorited Items", buttonPanel, 1, 0);
	    toRecipeButton = buildRedirectButton("Recipe Recommendations", buttonPanel, 0, 1);
	    toHistoryButton = buildRedirectButton("Consumption History", buttonPanel, 1, 1);
	    toSettingsButton = buildRedirectButton("User Preferences", buttonPanel, 0, 2);
	    toExportButton = buildRedirectButton("Backup Data", buttonPanel, 1, 2);
	    
	}

	
	private JButton buildRedirectButton(String buttonTxt, JPanel parent, int column, int row) {
		JPanel paddingWrapper = new CustomPanel(Color.black, new BorderLayout(), 30);
	    JButton button = new CustomButton(buttonTxt, this, 10);
	    button.setFont(new Font("Arial", Font.PLAIN, 20));
	    paddingWrapper.add(button);
	    parent.add(paddingWrapper, GridConstraintsSpec.stretchableFillConstraints(column, row, 0.5, 1, GridBagConstraints.BOTH));
	    
	    return button;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == toFridgeButton) {
			AppWindow.getWindow().loadNewView(new mainWindow());
		}
		else if (e.getSource() == toFavoritesButton) {
			AppWindow.getWindow().loadNewView(new FavoritesView());
		}
		else if (e.getSource() == toRecipeButton) {
			AppWindow.getWindow().loadNewView(new RecipeView(App.getInstance().getInventory()));
		}
		else if (e.getSource() == toHistoryButton) {
			AppWindow.getWindow().loadNewView(new HistoryView(App.getInstance().getHistory()));
		}
		else if (e.getSource() == toSettingsButton) {
			
		}
		
	}

}
