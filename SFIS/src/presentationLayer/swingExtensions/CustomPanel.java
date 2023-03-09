package presentationLayer.swingExtensions;

import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class CustomPanel extends JPanel {
	
	public CustomPanel() {
		
	}
	
	public CustomPanel(Color color, int border) {
		if (color != null)
			this.setBackground(color);
		
		if (border > 0)
			this.setBorder(BorderFactory.createEmptyBorder(border, border, border, border));
	}
	
	public CustomPanel(Color color, LayoutManager layout) {
		if (color != null)
			this.setBackground(color);
		
		if (layout != null)
			this.setLayout(layout);
	}
	
	public CustomPanel(Color color, LayoutManager layout, int border) {
		this(color, layout);
		
		if (border > 0)
			this.setBorder(BorderFactory.createEmptyBorder(border, border, border, border));
	}
}
