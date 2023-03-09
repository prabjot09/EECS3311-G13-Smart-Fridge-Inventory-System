package presentationLayer.swingExtensions;

import javax.swing.JComboBox;

public class DropDown<E> extends JComboBox<E> implements InputField {
	public DropDown(E[] values) { super(values); }
	
	public String getInput() { return (String) this.getSelectedItem(); }
	public void clearField() { this.setSelectedIndex(0); }
}
