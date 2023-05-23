package presentationLayer.swingExtensions;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class PromptText extends JTextField {

	public PromptText(String promptedText) {
		super (promptedText);
		
		addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent e) {
            	if (getText().equals(""))
            		setText(promptedText);
            }

            @Override
            public void focusGained(FocusEvent e) {
                if(getText().equals(promptedText)) {
                    setText("");
                }
            }
        });
			
		
	}
}
