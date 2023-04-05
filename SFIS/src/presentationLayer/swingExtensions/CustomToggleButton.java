package presentationLayer.swingExtensions;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class CustomToggleButton extends JButton {
	private String label1;
	private String label2;
	
	private boolean on;
	
	public CustomToggleButton(String label1, String label2, String init) {
		this.label1 = label1;
		this.label2 = label2;
		
		this.setText(label1);
		int minWidth = this.getPreferredSize().width;
		
		this.setText(label2);
		minWidth = Math.max(minWidth, this.getPreferredSize().width);
		
		this.setMinimumSize(new Dimension(minWidth, 1));
		this.setPreferredSize(new Dimension(minWidth, 1));
		this.setText(init);
	}
	
	public CustomToggleButton(String l1, String l2, String init, ActionListener listener, int padding) {
		this(l1, l2, init);
		
		this.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
		this.addActionListener(listener);
	}
	
	public void initToggle(boolean onOff) {
		this.on = onOff;
	}
	
	public boolean toggle() {
		this.on = !on;
		
		if (getText().equals(label1)) {
			this.setText(label2);
		}
		else {
			this.setText(label1);
		}
		
		return this.on;
	}
	
	public boolean isOn() {
		return this.on;
	}
}
