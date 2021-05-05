
package GUI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kari
 */
public class Validation {
    
    public boolean findInvalidField(String field){ 
        boolean value=false;
        Pattern pattern = Pattern.compile("[0-9.!#$%&'*+/=?^_`{|}~-]");
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
    
    public boolean validateKeysAlphanumeric(String key){    
        
        boolean value=false;
        Pattern pattern = Pattern.compile("\"\\\\d{8}[A-Z]\"");
        Matcher mather = pattern.matcher(key);
        if(mather.find()){  
            value=true;
        }   
        return value;  
    }
    
    
}
