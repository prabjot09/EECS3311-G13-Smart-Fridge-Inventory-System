package presentationLayer.swingExtensions;

import javax.swing.JTextField;

public class TextInput extends JTextField implements InputField {
	public TextInput(String string) { super(string); }

	public String getInput() { return this.getText(); }
	public void clearField() { this.setText(""); }
}