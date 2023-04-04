package presentationLayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import domainLayer.Pair;
import presentationLayer.swingExtensions.CustomPanel;
import presentationLayer.swingExtensions.GridConstraintsSpec;

public class CustomMultipleChoice<E> extends JPanel {
	
	private class CustomCheckBox extends JPanel {
		private JCheckBox checkBox;
		private E value;
		
		private CustomCheckBox(String desc, E value, Color bg) {
			checkBox = new JCheckBox(desc);
			checkBox.setFont(new Font("Arial", Font.PLAIN, 16));
			checkBox.setBackground(bg);
			this.value = value;
			
			this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			this.setLayout(new BorderLayout());
			this.setBackground(bg);
			
			this.add(checkBox);
		}
		
		private boolean isSelected() {
			return checkBox.isSelected();
		}
	}
	
	
	private List<CustomCheckBox> options;
	
	public CustomMultipleChoice (List<Pair<String, E>> options, Color bg, int cols) {
		this.options = new ArrayList<>();
		
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.setLayout(new GridBagLayout());
		this.setBackground(bg);
		
		GridBagConstraints c;
		
		int rows = options.size() / cols;
		
		for (int row = 0; row < rows; row++) {
			double optionWidth = 1.00 / cols;
			
			for (int col = 0; col < cols; col++) {
				Pair<String, E> optionInfo = options.get(row*cols + col);
				
				CustomCheckBox option = new CustomCheckBox(optionInfo.getA(), optionInfo.getB(), bg);
				this.options.add(option);
				c = GridConstraintsSpec.stretchableFillConstraints(col, row, optionWidth, 1, GridBagConstraints.HORIZONTAL);
				this.add(option, c);
			}
		}
		
		int itemsLeft = options.size() - rows*cols;
		if (itemsLeft == 0)
			return;
		
		// For the last row that may have different number of elements
		JPanel lastPanel = new CustomPanel(bg, new GridBagLayout());
		for (int col = 0; col < itemsLeft; col++) {
			Pair<String, E> optionInfo = options.get(col + rows*cols);
			double optionWidth = 1.00 / itemsLeft;
			
			CustomCheckBox option = new CustomCheckBox(optionInfo.getA(), optionInfo.getB(), bg);
			this.options.add(option);
			c = GridConstraintsSpec.stretchableFillConstraints(col, 0, optionWidth, 1, GridBagConstraints.HORIZONTAL);
			lastPanel.add(option, c);
		}
		
		c = GridConstraintsSpec.stretchableFillConstraints(0, rows, 1.0, 1.0, GridBagConstraints.HORIZONTAL);
		c.gridwidth = cols;
		this.add(lastPanel, c);
	}
	
	public List<E> getSelectedValues() {
		List<E> selected = new ArrayList<>();
		
		for (CustomCheckBox option: options) {
			if (option.isSelected()) {
				selected.add(option.value);
			}
		}
		
		return selected;
	}
}
