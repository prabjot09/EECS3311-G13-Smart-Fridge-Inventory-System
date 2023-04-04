package presentationLayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import presentationLayer.swingExtensions.CustomButton;
import presentationLayer.swingExtensions.CustomPanel;
import presentationLayer.swingExtensions.GridConstraintsSpec;

public class FileSelectionPanel extends JPanel implements ActionListener {
	
	private JLabel filePathLabel;
	private JButton fileButton;
	private int fileSelectionMode;

	public FileSelectionPanel(Color color, int fileSelectionMode) {
		this.setBackground(color);
		this.setLayout(new GridBagLayout());
		this.fileSelectionMode = fileSelectionMode;
		
		JPanel descWrapper = new CustomPanel(color, new BorderLayout(), 15);
		JLabel descLabel = new JLabel("Selected File: ");
		descLabel.setForeground(Color.black);
		descLabel.setFont(new Font("Arial", Font.BOLD, 20));
		descWrapper.add(descLabel);
		this.add(descWrapper, GridConstraintsSpec.stretchableFillConstraints(0, 0, 0, 0, GridBagConstraints.HORIZONTAL));
		
		JPanel outerWrapper = new CustomPanel(color, new BorderLayout(), 15);
		JPanel labelWrapper = new CustomPanel(Color.white, new BorderLayout(), 0);
		filePathLabel = new JLabel("");
		filePathLabel.setForeground(Color.black);
		filePathLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		filePathLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		labelWrapper.add(filePathLabel);
		outerWrapper.add(labelWrapper);
		this.add(outerWrapper, GridConstraintsSpec.stretchableFillConstraints(1, 0, 0.8, 0, GridBagConstraints.BOTH));
		
		JPanel horizontalSpace = new CustomPanel(color, new BorderLayout());
		this.add(horizontalSpace, GridConstraintsSpec.stretchableFillConstraints(2, 0, 0.2, 0, GridBagConstraints.BOTH));
		
	    JPanel selectorButtonWrapper = new CustomPanel(color, new BorderLayout(), 15);
		fileButton = new CustomButton("Select File Destination", this, 15);
		fileButton.setFont(new Font("Arial", Font.PLAIN, 18));
		selectorButtonWrapper.add(fileButton);
		this.add(selectorButtonWrapper, GridConstraintsSpec.stretchableFillConstraints(3, 0, 0, 0, GridBagConstraints.HORIZONTAL));
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == fileButton) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("File Location");
		    fc.setFileSelectionMode(fileSelectionMode);
		    fc.setAcceptAllFileFilterUsed(false);
		    
			int result = fc.showOpenDialog(AppWindow.getWindow());
			
			if (result != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(this, "No file has been selected.", "Notice", JOptionPane.WARNING_MESSAGE);
				filePathLabel.setText("");
				return;
			}
			else {
				filePathLabel.setText(fc.getSelectedFile().getAbsolutePath());
			}
		}
		
	}
	
	public String getSelectedFilePath() {
		String path = filePathLabel.getText();
		
		if (path.equals(""))
			return null;
		
		return path;
	}

}
