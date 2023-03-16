package presentationLayer.swingExtensions;

import java.awt.GridBagConstraints;

public class GridConstraintsSpec {
	public static GridBagConstraints coordinateConstraints(int x, int y) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		return c;
	}
	
	public static GridBagConstraints stretchableConstraints(int x, int y, double weightX, double weightY) {
		GridBagConstraints c = GridConstraintsSpec.coordinateConstraints(x, y);
		c.weightx = weightX;
		c.weighty = weightY;
		return c;
	}
	
	public static GridBagConstraints fillConstraints(int x, int y, int fillConstraint) {
		GridBagConstraints c = GridConstraintsSpec.coordinateConstraints(x, y);
		c.fill = fillConstraint;
		return c;
	}
	
	public static GridBagConstraints stretchableFillConstraints(int x, int y, double weightX, double weightY, int fillConstraint) {
		GridBagConstraints c = GridConstraintsSpec.stretchableConstraints(x, y, weightX, weightY);
		c.fill = fillConstraint;
		return c;
	}

}
