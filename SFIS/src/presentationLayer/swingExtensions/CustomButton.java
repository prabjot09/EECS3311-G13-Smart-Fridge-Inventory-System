package presentationLayer.swingExtensions;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class CustomButton extends JButton {
	public CustomButton() {
		
	}
	
	public CustomButton(String text, ActionListener listener) {
		if (text != null)
			this.setText(text);
		
		if (listener != null)
			this.addActionListener(listener);
	}
	
	public CustomButton(String text, ActionListener listener, int border) {
		this(text, listener);
		
		if (border > 0)
			this.setBorder(BorderFactory.createEmptyBorder(border, border, border, border));
	}
	
	public CustomButton(String text, ActionListener listener, int height, int width) {
		this(text, listener);
		
		this.setPreferredSize(new Dimension(width, height));
	}
}
