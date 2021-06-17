
package businessLogic;

import domain.LoginCredential;
import org.junit.Test;
import static org.junit.Assert.*;


public class LoginCredentialDAOTest {
    
    @Test
    public void testRegister() throws Exception {
        System.out.println("register");
        LoginCredential credential = new LoginCredential("ArenVald","12345","4065161");
        LoginCredentialDAO loginCredentialDAO = new LoginCredentialDAO();
        assertTrue(loginCredentialDAO.registerSuccesful(credential));
    }

    @Test
    public void testSearchLoginCredential() throws Exception {
        LoginCredential credential = new LoginCredential("ArenVald","12345","4065161");
        String passwordExpected = "12345";
        LoginCredentialDAO loginCredentialDAO = new LoginCredentialDAO();
        System.out.println(loginCredentialDAO.searchLoginCredential(credential).getPassword());
        assertEquals("12345",loginCredentialDAO.searchLoginCredential(credential).getPassword());
    }
    
}
