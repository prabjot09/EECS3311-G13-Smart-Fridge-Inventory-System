package presentationLayer;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import appLayer.App;
import domainLayer.DBProxy;
import presentationLayer.swingExtensions.CustomButton;
import presentationLayer.swingExtensions.CustomPanel;
import presentationLayer.swingExtensions.DropDown;
import presentationLayer.swingExtensions.GridConstraintsSpec;

public class SettingsView extends AppFrameView implements ActionListener {
	
	private JButton backButton;
	
	private DropDown<String> expiryOptions;
	private JButton smartFeatureToggle;
	private boolean smartFeatureOn;
	private DropDown<String> groceryOptions;
	
	
	public SettingsView() {
		this.setBackground(Color.black);
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints c;
		
		JPanel titlePanel = new CustomPanel(Color.black, new FlowLayout(FlowLayout.CENTER), 40);
		c = GridConstraintsSpec.stretchableFillConstraints(0, 0, 1, 0, GridBagConstraints.HORIZONTAL);
		this.add(titlePanel, c);
		
		JLabel titleLabel = new JLabel("User Preferences");
	    titleLabel.setForeground(Color.white);
	    titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
	    titlePanel.add(titleLabel);
	    
	    JPanel backPanel = new CustomPanel(Color.black, new FlowLayout(FlowLayout.LEFT));
	    backPanel.setBorder(BorderFactory.createEmptyBorder(0,50,0,50));
	    c = GridConstraintsSpec.stretchableFillConstraints(0, 1, 1, 0, GridBagConstraints.HORIZONTAL);
	    this.add(backPanel, c);
	    backButton = new CustomButton("Back", this);
	    backPanel.add(backButton);
	    
	    JPanel settingsPanel = new CustomPanel(new Color(50, 50, 50), new GridBagLayout());
	    settingsPanel.setBorder(BorderFactory.createLineBorder(Color.black, 50));
	    c = GridConstraintsSpec.stretchableFillConstraints(0, 2, 1, 0, GridBagConstraints.BOTH);
	    this.add(settingsPanel, c);
	    
	    JPanel wrapperPanel = new CustomPanel(settingsPanel.getBackground(), new FlowLayout(FlowLayout.LEFT));
	    c = GridConstraintsSpec.stretchableFillConstraints(0, 0, 0.9, 0, GridBagConstraints.HORIZONTAL);
	    settingsPanel.add(wrapperPanel, c);
	    JLabel expirySettingLabel = new JLabel("Expiry Warning Threshold (Days)");
	    expirySettingLabel.setForeground(Color.white);
	    expirySettingLabel.setFont(new Font("Arial", Font.BOLD, 16));
	    expirySettingLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	    wrapperPanel.add(expirySettingLabel);
	    
	    wrapperPanel = new CustomPanel(settingsPanel.getBackground(), new GridBagLayout(), 20);
	    c = GridConstraintsSpec.stretchableFillConstraints(1, 0, 0.1, 0, GridBagConstraints.HORIZONTAL);
	    settingsPanel.add(wrapperPanel, c);
	    String[] expiryOptionsArr = {"2", "3", "4", "5", "6", "7", "8", "9", "10"};
	    expiryOptions = new DropDown<>(expiryOptionsArr);
	    expiryOptions.setFont(new Font("Arial", Font.PLAIN, 16));
	    expiryOptions.setSelectedItem("" + App.getInstance().getSettings().getExpirationWarningDays());
	    expiryOptions.addActionListener(this);
	    c = GridConstraintsSpec.stretchableFillConstraints(0, 0, 1, 1, GridBagConstraints.BOTH);
	    wrapperPanel.add(expiryOptions, c);
	    
	    
	    wrapperPanel = new CustomPanel(settingsPanel.getBackground(), new FlowLayout(FlowLayout.LEFT));
	    c = GridConstraintsSpec.stretchableFillConstraints(0, 1, 0.9, 0, GridBagConstraints.HORIZONTAL);
	    settingsPanel.add(wrapperPanel, c);
	    JLabel smartFeatureLabel = new JLabel("Smart Feature Toggle");
	    smartFeatureLabel.setForeground(Color.white);
	    smartFeatureLabel.setFont(new Font("Arial", Font.BOLD, 16));
	    smartFeatureLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	    wrapperPanel.add(smartFeatureLabel);
	    
	    wrapperPanel = new CustomPanel(settingsPanel.getBackground(), new GridBagLayout(), 20);
	    c = GridConstraintsSpec.stretchableFillConstraints(1, 1, 0.1, 0, GridBagConstraints.HORIZONTAL);
	    settingsPanel.add(wrapperPanel, c);
	    smartFeatureOn = App.getInstance().getSettings().isSmartFeaturesEnabled();
	    smartFeatureToggle = new CustomButton(smartFeatureOn ? "On" : "Off", this, 10);	    
	    c = GridConstraintsSpec.stretchableFillConstraints(0, 0, 1, 1, GridBagConstraints.BOTH);
	    wrapperPanel.add(smartFeatureToggle, c);
	    
	    
	    
	    wrapperPanel = new CustomPanel(settingsPanel.getBackground(), new FlowLayout(FlowLayout.LEFT));
	    c = GridConstraintsSpec.stretchableFillConstraints(0, 2, 0.9, 0, GridBagConstraints.HORIZONTAL);
	    settingsPanel.add(wrapperPanel, c);
	    JLabel groceryThresholdLabel = new JLabel("Automatic Grocery List Addition Threshold (%)");
	    groceryThresholdLabel.setForeground(Color.white);
	    groceryThresholdLabel.setFont(new Font("Arial", Font.BOLD, 16));
	    groceryThresholdLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	    wrapperPanel.add(groceryThresholdLabel);
	    
	    wrapperPanel = new CustomPanel(settingsPanel.getBackground(), new GridBagLayout(), 20);
	    c = GridConstraintsSpec.stretchableFillConstraints(1, 2, 0.1, 0, GridBagConstraints.HORIZONTAL);
	    settingsPanel.add(wrapperPanel, c);
	    String[] groceryThresholdOptions = {"0", "5", "10", "15", "20", "25", "30", "35", "40"};
	    groceryOptions = new DropDown<>(groceryThresholdOptions);
	    groceryOptions.setFont(new Font("Arial", Font.PLAIN, 16));
	    groceryOptions.addActionListener(this);
	    groceryOptions.setSelectedItem("" + App.getInstance().getSettings().getAddGroceryListThreshold());
	    c = GridConstraintsSpec.stretchableFillConstraints(0, 0, 1, 1, GridBagConstraints.BOTH);
	    wrapperPanel.add(groceryOptions, c);
	    
	    c = GridConstraintsSpec.stretchableFillConstraints(0, 3, 1, 1, GridBagConstraints.BOTH);
	    this.add(new CustomPanel(Color.black, null), c);
	    
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backButton) {
			this.saveData();
			AppWindow.getWindow().loadPreviousWindow();
		}
		else if (e.getSource() == smartFeatureToggle) {
			if (smartFeatureOn) {
				smartFeatureToggle.setText("Off");
			}
			else {
				smartFeatureToggle.setText("On");
			}
			smartFeatureOn = !smartFeatureOn;
			
			App.getInstance().getSettings().setSmartFeaturesEnabled(smartFeatureOn);
		}
		else if (e.getSource() == expiryOptions) {
			int expiryThresholdValue = Integer.parseInt(expiryOptions.getInput());
			App.getInstance().getSettings().setExpirationWarningDays(expiryThresholdValue);
		}
		else if (e.getSource() == groceryOptions) {
			int groceryThresholdValue = Integer.parseInt(groceryOptions.getInput());
			App.getInstance().getSettings().setAddGroceryListThreshold(groceryThresholdValue);
		}
	}

	@Override
	public void saveData() {
		App.getInstance().getSettings().saveToDatabase();
	}
}
