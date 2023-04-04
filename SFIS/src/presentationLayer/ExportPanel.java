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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import presentationLayer.swingExtensions.CustomButton;
import presentationLayer.swingExtensions.GridConstraintsSpec;
import presentationLayer.swingExtensions.CustomPanel;

public class ExportPanel extends JPanel implements ActionListener {

	private FileSelectionPanel filePanel;
	private JButton exportButton;
	
	public ExportPanel(Color color) {
		this.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		this.setLayout(new GridBagLayout());
		this.setBackground(color);
		
		GridBagConstraints c; 
		
		filePanel = new FileSelectionPanel(color, JFileChooser.DIRECTORIES_ONLY); 
		c = GridConstraintsSpec.stretchableFillConstraints(0, 0, 1, 0, GridBagConstraints.HORIZONTAL);
		this.add(filePanel, c);
		
		JPanel verticalSpace = new CustomPanel(color, new BorderLayout());
		c = GridConstraintsSpec.stretchableFillConstraints(0, 1, 1.0, 1.0, GridBagConstraints.BOTH);
		this.add(verticalSpace, c);
		
		JPanel exportWrapper = new CustomPanel(color, new FlowLayout(FlowLayout.CENTER), 30);
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
			
			File exportFile = new File(filePanel.getSelectedFilePath());
			// TODO: Export operation
		}
	}

}
