
package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class TextFieldLimited extends TextField {  
    private int maxlength;
    
    public TextFieldLimited() {
        this.maxlength = 100;
    }
    
    public void setMaxLength(int maxlength) {
        this.maxlength = maxlength;
    }
    
    @Override
    public void replaceText(int start, int end, String text) {

        if (text.equals("")) {
            super.replaceText(start, end, text);
        } else if (getText().length() < maxlength) {
            super.replaceText(start, end, text);
        }
    }
    
   @Override
    public void replaceSelection(String text) {
        if (text.equals("")) {
            super.replaceSelection(text);
        } else if (getText().length() < maxlength) {
            if (text.equals("")) {
               super.replaceSelection(text);
            } else if (getText().length() < maxlength) {
                if (text.length() > maxlength - getText().length()) {
                     text = text.substring(0, maxlength- getText().length());
                }
            super.replaceSelection(text);
            }
        }
 
    }

}
