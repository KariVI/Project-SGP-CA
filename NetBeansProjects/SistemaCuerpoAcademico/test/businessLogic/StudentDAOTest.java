
package businessLogic;

import domain.Student;
import log.BusinessException;
import org.junit.Test;
import static org.junit.Assert.*;

public class StudentDAOTest {


    @Test
    public void testSave() throws BusinessException {
        System.out.println("savedSucessful");
        Student student = new Student("S19014080", "Arturo Villa Lopez");
        StudentDAO studentDAO = new StudentDAO();
        assertTrue(studentDAO.savedSucessful(student));
    }

    @Test
    public void testGetByEnrollment() throws BusinessException{
        System.out.println("getByEnrollment");
        String enrollment = "S19014023";
        StudentDAO studentDAO = new StudentDAO();
        boolean value=true;
        Student studentExpected = new Student("S19014023", "Karina Valdes Iglesias");
         
            Student studentResult = studentDAO.getByEnrollment(enrollment);
            
        
        assertTrue(studentResult.equals(studentExpected));
        
    }
    @Test
     public void testGetByEnrollmentFailed() {
        System.out.println("getByEnrollmentFailed");
        String enrollment = "S19014024";
        StudentDAO studentDAO = new StudentDAO();
        boolean value=true;
        Student expResult = new Student("S19014023", "Karina Valdes Iglesias");
        try{    
            Student result = studentDAO.getByEnrollment(enrollment);
        }catch(BusinessException ex){
            value=false;
        }
        assertFalse(value);
        
    }
    
}
