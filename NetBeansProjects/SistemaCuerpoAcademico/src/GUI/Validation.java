
package GUI;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;


public class Validation {
    public boolean validateStringSize(String TextField, int size){
        boolean value = false;
        if(TextField.length() <= size){
            value = true;
        }
        return value;
    }
}
