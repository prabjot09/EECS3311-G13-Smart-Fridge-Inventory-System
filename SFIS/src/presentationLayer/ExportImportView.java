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
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import presentationLayer.swingExtensions.CustomButton;
import presentationLayer.swingExtensions.CustomPanel;
import presentationLayer.swingExtensions.GridConstraintsSpec;

public class ExportImportView extends JPanel implements ActionListener{
	
	private JButton exportButton;
	private JButton importButton;
	
	private JButton selectedButton;
	
	private JPanel exportPanel;
	private JPanel importPanel;
	private JPanel mainPanel;
	private JPanel selectedPanel;
	
	public ExportImportView() {
		this.setBackground(Color.black);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel titleLabel = new JLabel("Data Backup");
	    titleLabel.setForeground(Color.white);
	    titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
	    
	    JPanel titlePanel = new CustomPanel(Color.black, new FlowLayout(FlowLayout.CENTER), 20);
	    titlePanel.add(titleLabel);
	    this.add(titlePanel);
	    
	    // TODO: Add multi-select for import/export like addWindow
	    JPanel selectionPanel = new CustomPanel(Color.black, new GridBagLayout(), 30);
	    this.add(selectionPanel);
	    
	    GridBagConstraints c = GridConstraintsSpec.stretchableFillConstraints(0, 0, 0.5, 0.0, GridBagConstraints.HORIZONTAL);
	    importButton = new CustomButton("Import", this, 20);
	    importButton.setFont(new Font("Arial", Font.BOLD, 24));
	    selectionPanel.add(importButton, c);
	    
	    c = GridConstraintsSpec.stretchableFillConstraints(1, 0, 0.5, 0.0, GridBagConstraints.HORIZONTAL);
	    exportButton = new CustomButton("Export", this, 20);
	    exportButton.setFont(new Font("Arial", Font.BOLD, 24));
	    selectionPanel.add(exportButton, c);
	    
	    mainPanel = new CustomPanel(Color.white, new BorderLayout(), 10);
	    c = GridConstraintsSpec.stretchableFillConstraints(0, 1, 1.0, 1.0, GridBagConstraints.BOTH);
	    c.gridwidth = 2;
	    selectionPanel.add(mainPanel, c);
	    
	    buildExportPanel();
	    buildImportPanel();
	    
	    selectedPanel = exportPanel;
	    selectedButton = exportButton;
	    selectOption(importButton, importPanel);
	}

	public void buildExportPanel() {
		exportPanel = new ExportPanel(new Color(200, 200, 200));
	}
	
	public void buildImportPanel() {
		importPanel = new ImportPanel(new Color(200, 200, 200));
	}
	
	private void selectOption(JButton button, JPanel newPanel) {
		deselectOption(selectedButton, selectedPanel);
		
		button.setBackground(new Color(200, 200, 200));
		button.setForeground(Color.black);
		mainPanel.add(newPanel);
		
		selectedPanel = newPanel;
		selectedButton = button;
		
		mainPanel.repaint();
		mainPanel.revalidate();
	}
	
	
	private void deselectOption(JButton button, JPanel oldPanel) {
		if (button == null)
			return;
		
		button.setBackground(new Color(50, 50, 50));
		button.setForeground(Color.gray);
		mainPanel.remove(oldPanel);
	}	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == importButton) {
			if (selectedButton == importButton)
				return;
			
			selectOption(importButton, importPanel);
		} 
		else if (e.getSource() == exportButton) {
			if (selectedButton == exportButton)
				return;
			
			selectOption(exportButton, exportPanel);
		}
	}
}
