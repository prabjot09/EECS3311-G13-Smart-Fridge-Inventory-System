package presentationLayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import presentationLayer.swingExtensions.CustomButton;
import presentationLayer.swingExtensions.CustomPanel;
import presentationLayer.swingExtensions.GridConstraintsSpec;

public class ImportPanel extends JPanel implements ActionListener{

	private FileSelectionPanel filePanel;
	private JButton importButton;
	
	public ImportPanel(Color color) {
		this.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		this.setLayout(new GridBagLayout());
		this.setBackground(color);
		
		GridBagConstraints c; 
		
		filePanel = new FileSelectionPanel(color, JFileChooser.FILES_ONLY); 
		c = GridConstraintsSpec.stretchableFillConstraints(0, 0, 1, 0, GridBagConstraints.HORIZONTAL);
		this.add(filePanel, c);	
		
		JPanel optionSelectionPanel = new CustomPanel(color, new BorderLayout());
		c = GridConstraintsSpec.stretchableFillConstraints(0, 1, 1.0, 1.0, GridBagConstraints.BOTH);
		this.add(optionSelectionPanel, c);
		
		JPanel importWrapper = new CustomPanel(color, new FlowLayout(FlowLayout.CENTER), 15);
		importButton = new CustomButton("Import Data", this, 15);
		importButton.setFont(new Font("Arial", Font.PLAIN, 18));
		importWrapper.add(importButton);
		c = GridConstraintsSpec.stretchableFillConstraints(0, 2, 1, 0, GridBagConstraints.HORIZONTAL);
		this.add(importWrapper, c);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
