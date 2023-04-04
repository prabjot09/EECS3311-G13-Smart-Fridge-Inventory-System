package presentationLayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import appLayer.App;
import domainLayer.DBProxy;
import domainLayer.Pair;
import persistenceLayer.RealDB;
import presentationLayer.swingExtensions.CustomButton;
import presentationLayer.swingExtensions.GridConstraintsSpec;
import presentationLayer.swingExtensions.CustomPanel;

public class ExportPanel extends JPanel implements ActionListener {

	private FileSelectionPanel filePanel;
	private JButton exportButton;
	private CustomMultipleChoice<String> optionMenu;
	
	public ExportPanel(Color color) {
		this.setBorder(BorderFactory.createEmptyBorder(15, 30, 10, 30));
		this.setLayout(new GridBagLayout());
		this.setBackground(color);
		
		GridBagConstraints c; 
		
		filePanel = new FileSelectionPanel(color, JFileChooser.DIRECTORIES_ONLY); 
		c = GridConstraintsSpec.stretchableFillConstraints(0, 0, 1, 0, GridBagConstraints.HORIZONTAL);
		this.add(filePanel, c);
		
		JPanel optionsWrapper = new CustomPanel(color, new GridBagLayout(), 10);
		c = GridConstraintsSpec.stretchableFillConstraints(0, 1, 1.0, 1.0, GridBagConstraints.BOTH);
		this.add(optionsWrapper, c);
		
		JPanel optionTitlePanel = new CustomPanel(color, new FlowLayout(FlowLayout.LEFT), 5);
		c = GridConstraintsSpec.stretchableFillConstraints(0, 0, 1.0, 0.0, GridBagConstraints.HORIZONTAL);
		optionsWrapper.add(optionTitlePanel, c);
		
		JLabel optionTitleLabel = new JLabel("Data to be Exported: ");
		optionTitleLabel.setForeground(new Color(70, 70, 70));
		optionTitleLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		optionTitlePanel.add(optionTitleLabel);
		
		JPanel optionSelectionPanel = new CustomPanel(color, new BorderLayout());
		optionSelectionPanel.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70), 3));
		c = GridConstraintsSpec.stretchableFillConstraints(0, 1, 1.0, 1.0, GridBagConstraints.BOTH);
		optionsWrapper.add(optionSelectionPanel, c);
		
		List<Pair<String, String>> options = new ArrayList<>();
		options.add(new Pair<String, String>("Fridge Items", RealDB.FRIDGE));
		options.add(new Pair<String, String>("Favorited Items", RealDB.FAVORITES));
		options.add(new Pair<String, String>("Grocery List", RealDB.GROCERIES));
		options.add(new Pair<String, String>("User History", RealDB.HISTORY + " " + RealDB.DATE));
		options.add(new Pair<String, String>("User Settings", RealDB.SETTINGS));
		optionMenu = new CustomMultipleChoice<String>(options, color, 2);
		optionSelectionPanel.add(optionMenu);
		
		JPanel exportWrapper = new CustomPanel(color, new FlowLayout(FlowLayout.CENTER), 15);
		exportButton = new CustomButton("Export Data", this, 15);
		exportButton.setFont(new Font("Arial", Font.PLAIN, 18));
		exportWrapper.add(exportButton);
		c = GridConstraintsSpec.stretchableFillConstraints(0, 2, 1, 0, GridBagConstraints.HORIZONTAL);
		this.add(exportWrapper, c);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exportButton) {
			if (filePanel.getSelectedFilePath() == null) {
				JOptionPane.showMessageDialog(this, "Select a file location before proceeding", "Notice", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			App.getInstance().saveData();
			
			File exportFile = new File(filePanel.getSelectedFilePath());
			
			List<String> selectedTables = optionMenu.getSelectedValues();
			String tableList = "";
			for (int i = 0; i < selectedTables.size(); i++) {
				tableList += selectedTables.get(i);
				
				if (i < selectedTables.size() - 1)
					tableList += " ";
			}
			
			DBProxy.getInstance().exportData(tableList, exportFile);
		}
	}

}
