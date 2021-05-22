
package businessLogic;

import domain.Student;
import log.BusinessException;
import org.junit.Test;
import static org.junit.Assert.*;

public class StudentDAOTest {


    @Test
    public void testSave() throws BusinessException {
        System.out.println("savedSucessful");
        Student student = new Student("S19014013", "Mariana Yazmin Vargas Segura ");
        StudentDAO instance = new StudentDAO();
        assertTrue(instance.savedSucessful(student));
    }

    @Test
    public void testGetByEnrollment() throws BusinessException {
        System.out.println("getByEnrollment");
        String enrollment = "S19014023";
        StudentDAO instance = new StudentDAO();
        Student expResult = new Student("S19014023", "Karina Valdes Iglesias");
        Student result = instance.getByEnrollment(enrollment);
        assertTrue(expResult.equals(result));
        
    }
    @Test
     public void testGetByEnrollmentFailed() throws BusinessException {
        System.out.println("getByEnrollmentFailed");
        String enrollment = "S19014024";
        StudentDAO instance = new StudentDAO();
        Student expResult = new Student("S19014024", "Laura Leticia Rodriguez Lopez");
        Student result = instance.getByEnrollment(enrollment);
        assertFalse(expResult.equals(result));
        
    }
    
}
