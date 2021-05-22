
package GUI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

<<<<<<< HEAD
/**
 *
 * @author kari
 */
public class Validation {
    
    public boolean findInvalidField(String field){ 
        boolean value=false;
        Pattern pattern = Pattern.compile("[0-9!#$%&'*+/=?^_{|}~-]");
=======

public class Validation {
    public static boolean findInvalidField(String field){ 
        boolean value=false;
        Pattern pattern = Pattern.compile("[0-9!#$%&'*+/=?^_`{|}~-]");
>>>>>>> MarianaChangesGUI
        Matcher mather = pattern.matcher(field);
        if(mather.find()){  
            value=true;
        }   
        return value;  
    }
    
    public boolean validateDate(String date){   
        
        boolean value=false;
        Pattern pattern = Pattern.compile("(\\d{2})/(\\d{2})/(\\d{2,4})");
        Matcher mather = pattern.matcher(date);
        if(mather.find()){  
            value=true;
        }   
        return value;  
    }
    
    public boolean findInvalidKeyAlphanumeric(String key){    
        boolean value=false;
        Pattern pattern = Pattern.compile("[.!#$%&'*+/=?^_`{|}~-]");
        Matcher mather = pattern.matcher(key);
        if(mather.find()){  
            value=true;
        }   
        return value;  
    }
    
<<<<<<< HEAD
    public boolean validateNumberField(String field){
       boolean value=false;
              try {
                    Integer.parseInt(field);
                    value=true;
               } catch (NumberFormatException exception) {
                   AlertMessage alertMessage=new AlertMessage();
                   alertMessage.showAlert("Inserte un formato de número correcto en número de lgac");
               }

              return value;

    }
    
    
=======
    public static boolean validateHour(String hour){    
        boolean value=false;
        Pattern pattern = Pattern.compile("([0-1][0-9]|2[0-3])(:)([0-5][0-9])");
        Matcher mather = pattern.matcher(hour);
        if(mather.find()){  
            value = true;
        }   
        return value;  
    }
    
>>>>>>> MarianaChangesGUI
}
