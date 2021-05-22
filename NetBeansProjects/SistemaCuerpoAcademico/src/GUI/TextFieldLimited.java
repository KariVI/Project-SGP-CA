<<<<<<< HEAD
package GUI;

import javafx.scene.control.TextField;
=======
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
>>>>>>> MarianaChangesGUI


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
<<<<<<< HEAD
=======
        // Delete or backspace user input.
>>>>>>> MarianaChangesGUI
        if (text.equals("")) {
            super.replaceText(start, end, text);
        } else if (getText().length() < maxlength) {
            super.replaceText(start, end, text);
        }
    }
    
   @Override
    public void replaceSelection(String text) {
<<<<<<< HEAD
        if (text.equals("")) {
            super.replaceSelection(text);
        } else if (getText().length() < maxlength) {
=======
        // Delete or backspace user input.
        if (text.equals("")) {
            super.replaceSelection(text);
        } else if (getText().length() < maxlength) {
            // Add characters, but don't exceed maxlength.
>>>>>>> MarianaChangesGUI
            if (text.length() > maxlength - getText().length()) {
                text = text.substring(0, maxlength- getText().length());
            }
            super.replaceSelection(text);
        }
    }
 
<<<<<<< HEAD
}
   
 
=======
}
>>>>>>> MarianaChangesGUI
