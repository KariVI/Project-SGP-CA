package GUI;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.DatePicker;



public class Validation {
    public boolean findInvalidField(String field){ 
        boolean value=false;
        Pattern pattern = Pattern.compile("[!#$%'*+/=?^_`{|}~]");
        Matcher mather = pattern.matcher(field);
        if(mather.find()){  
            value=true;
        }   
        return value;  
    }
    
     public boolean emptyField(String field){
        boolean value = false;
        if(field.isEmpty()){
            value=true;
        }
        else if(field.trim().length()==0){
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
    
    public boolean validateCorrectHours(String startHour, String finishHour){
        boolean value = true;
        if(startHour.compareTo(finishHour) >= 0){
            value = false;
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
    
    public boolean validateNumberField(String field){
       boolean value=false;
              try {
                    Integer.parseInt(field);
                    value=true;
               } catch (NumberFormatException exception) {
                   AlertMessage alertMessage=new AlertMessage();
                   alertMessage.showAlertValidateFailed("Inserte un formato de número correcto");
               }

              return value;

    }
    
    

    public  boolean validateHour(String hour){    
        boolean value=false;
        Pattern pattern = Pattern.compile("([0-1][0-9]|2[0-3])(:)([0-5][0-9])");
        Matcher mather = pattern.matcher(hour);
        if(mather.find()){  
            value = true;
        }   
        return value;  
    }
    
    public boolean validateDates(DatePicker dpStartDate, DatePicker dpEndDate){
        boolean value = false;       
        if(dpStartDate.getValue()!= null && dpEndDate.getValue()!= null){
           LocalDate dateStart= dpStartDate.getValue();
           LocalDate dateEnd = dpEndDate.getValue();
          if(dateEnd.isAfter(dateStart)){ 
              value=true;
          }
          
       }
        
       return value;
   }
}
