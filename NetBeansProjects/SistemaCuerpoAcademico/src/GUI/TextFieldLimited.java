package GUI;

import javafx.scene.control.TextField;


public class TextFieldLimited extends TextField {
    private int maxlength;

    public TextFieldLimited() {
        this.maxlength = 100;
    }

    public void setMaxlength(int maxlength) {
        this.maxlength = maxlength;
    }

    @Override
    public void replaceText(int start, int end, String text) {
        // Delete or backspace user input.
        if (text.equals("")) {
            super.replaceText(start, end, text);
        } else if (getText().length() < maxlength) {
            super.replaceText(start, end, text);
        }
    }

   
 
}