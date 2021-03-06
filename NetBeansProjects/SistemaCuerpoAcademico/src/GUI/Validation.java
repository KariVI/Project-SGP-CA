package GUI;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.DatePicker;



public class Validation {
    public boolean findInvalidField(String field){ 
        boolean value = false;
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
    
  
    
    public boolean existsInvalidCharactersForEmail(String email){
        boolean invalidCharacters = false;
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(email);
        if(!matcher.find()){
           invalidCharacters = true; 
        }
        return invalidCharacters;
    }
    
    public boolean existsInvalidCharactersForCurp(String textToValidate){
        boolean invalidCharacters = false;
        String validText = "[A-Z]{1}[AEIOU]{1}[A-Z]{2}[0-9]{2}" + "(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])" +
                   "[HM]{1}" + "(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)" +
                   "[B-DF-HJ-NP-TV-Z]{3}" + "[0-9A-Z]{1}[0-9]{1}$";
        Pattern pattern = Pattern.compile(validText);
        Matcher matcher = pattern.matcher(textToValidate);
        if(!matcher.find()){
            invalidCharacters = true;
        }    
        return invalidCharacters;
    }
}
