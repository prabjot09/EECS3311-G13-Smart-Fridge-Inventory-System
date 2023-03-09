package presentationLayer.swingExtensions;

import java.awt.Color;

import javax.swing.BoxLayout;

public class CustomBoxPanel extends CustomPanel{
	public CustomBoxPanel(Color color, int layout) {
		if (color != null)
			this.setBackground(color);
		
		this.setLayout(new BoxLayout(this, layout));
	}
	
	public CustomBoxPanel(Color color, int layout, int border) {
		super(color, border);
		
		this.setLayout(new BoxLayout(this, layout));
	}
}
