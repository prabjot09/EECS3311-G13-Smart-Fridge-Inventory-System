package presentationLayer;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class PromptText extends JTextField {

	public PromptText(String promptedText) {
		super (promptedText);
		
		addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent e) {
                if(getText().isEmpty()) {
                    setText(promptedText);
                }
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
