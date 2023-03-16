package presentationLayer.swingExtensions;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LabelledInputField extends JPanel{
	
	private JTextField inputText;
	
	public LabelledInputField(Color bg, Color fontColor, String label, int fontSize) {
		this.setBackground(bg);
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel inputLabel = new JLabel(label);
		inputLabel.setForeground(fontColor);
		inputLabel.setFont(new Font("Arial", Font.BOLD, fontSize));
		c = GridConstraintsSpec.stretchableFillConstraints(0, 0, 0.1, 1, GridBagConstraints.BOTH);
	    this.add(inputLabel, c);
	    
		inputText = new JTextField("");
		inputText.setFont(new Font("Arial", Font.PLAIN, fontSize));
		inputText.setBackground(Color.gray);
	    c = GridConstraintsSpec.stretchableFillConstraints(1, 0, 0.9, 1, GridBagConstraints.BOTH);
	    this.add(inputText, c);
	}
	
	public LabelledInputField(Color bg, Color fontColor, String label, int labelFontSize, int inputFontSize, int pad) {
		this.setBackground(bg);
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel inputLabel = new JLabel(label);
		inputLabel.setForeground(fontColor);
		inputLabel.setFont(new Font("Arial", Font.BOLD, labelFontSize));
		inputLabel.setBorder(BorderFactory.createEmptyBorder(pad, pad, pad, pad));
		c = GridConstraintsSpec.stretchableFillConstraints(0, 0, 0.1, 1, GridBagConstraints.BOTH);
	    this.add(inputLabel, c);
	    
	    inputText = new JTextField("");
		inputText.setFont(new Font("Arial", Font.PLAIN, inputFontSize));
		inputText.setBackground(Color.gray);
		inputText.setBorder(BorderFactory.createEmptyBorder(pad, pad, pad, pad));
		c = GridConstraintsSpec.stretchableFillConstraints(1, 0, 0.9, 1, GridBagConstraints.BOTH);
	    this.add(inputText, c);
	}

	public JTextField getTextField() {
		return this.inputText;
	}
}
