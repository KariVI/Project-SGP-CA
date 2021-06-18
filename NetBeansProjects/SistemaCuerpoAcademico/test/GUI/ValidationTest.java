
package GUI;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidationTest {
    
    public ValidationTest() {
    }

    @Test
    public void testFindInvalidFieldFailed() {
        System.out.println("findInvalidField");
        String field = "Karina Valdes Iglesias";
        Validation validation= new Validation();
        boolean result = validation.findInvalidField(field);
        assertFalse(result);
    }
    @Test
    public void testFindInvalidField(){ 
          System.out.println("findInvalidField");
        String field = "Karina///$$$ Valdes***Iglesias";
        Validation validation= new Validation();
        boolean result = validation.findInvalidField(field);
        assertTrue(result);
    
    }
    
    @Test
    public void testValidateDate(){
        System.out.println("validateDate");
        String date="11/10/2021";
        Validation validation= new Validation();
        boolean result= validation.validateDate(date);
        assertTrue(result);

    }
       
    @Test
    public void testValidateDateFailed(){
        System.out.println("validateDate");
        String date="11/Octubre/2021";
        Validation validation= new Validation();
        boolean result= validation.validateDate(date);
        assertFalse(result);

    }
    
    @Test 
    public void testValidateKeyAlphanumeric(){  
        String key="JDOEIJ80/&%4";
        Validation validation= new Validation();
        boolean result= validation.findInvalidKeyAlphanumeric(key);
        assertTrue(result);
        
    }
    
    @Test
       public void testValidateKeyAlphanumericFailed(){  
        String key="JDOEIJ804";
        Validation validation= new Validation();
        boolean result= validation.findInvalidKeyAlphanumeric(key);
        assertFalse(result);
    }
    
    @Test
     public void testValidateCorrectHour(){
         String startHour = "09:20";
         String finishHour = "22:21";
         Validation validation= new Validation();
         assertTrue(validation.validateCorrectHours(startHour, finishHour));
     }
     
    @Test
       public void testValidateCorrectHourFailed(){
         String startHour = "22:21";
         String finishHour = "09:20";
         Validation validation= new Validation();
         assertFalse(validation.validateCorrectHours(startHour, finishHour));
     }
       
     @Test
     public void testvalidateNumberField(){ 
         String number= "12";
          Validation validation= new Validation();
         assertTrue(validation.validateNumberField(number));
         
     }
     
     @Test
     public void testvalidateNumberFieldFailed(){ 
         String number= "Doce";
         boolean value=true;
          Validation validation= new Validation();
          try{
              validation.validateNumberField(number);
          }catch(ExceptionInInitializerError ex){    
              value=false;
          }
         assertFalse(value);
     }
         
 }


