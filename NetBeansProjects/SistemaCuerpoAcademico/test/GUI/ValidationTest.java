
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
        String field = "Karina/&? Valdes -?Iglesias";
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
    
}
