package presentationLayer.swingExtensions;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CustomLabel extends JLabel {

	public CustomLabel(String label, String fontFamily, int fontStyle, int fontSize, Color color) {
		super(label);
		this.setFont(new Font(fontFamily, fontStyle, fontSize));
		this.setForeground(color);
	}
	
	public JPanel alignText(Color bg, int alignment, int padding) {
		JPanel wrap = new CustomPanel(bg, new FlowLayout(alignment), padding);
		wrap.add(this);
		return wrap;
	}
	
	public JPanel alignText(Color bg, int alignment, int vPad, int hPad) {
		JPanel wrap = new CustomPanel(bg, new FlowLayout(alignment));
		wrap.setBorder(BorderFactory.createEmptyBorder(vPad, hPad, vPad, hPad));
		wrap.add(this);
		return wrap;
	}
	
}
