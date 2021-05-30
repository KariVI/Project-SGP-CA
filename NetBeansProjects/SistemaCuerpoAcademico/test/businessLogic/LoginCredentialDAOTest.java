/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import GUI.Validation;
import domain.LoginCredential;
import java.sql.Blob;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mariana
 */
public class LoginCredentialDAOTest {
    
    public LoginCredentialDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of register method, of class LoginCredentialDAO.
     */
    @Test
    public void testRegister() throws Exception {
        System.out.println("register");
        Validation validation = new Validation();
        Blob password = validation.stringToBlob("12345");
        LoginCredential credential = new LoginCredential("JuanPer",password,"8325134");
        LoginCredentialDAO loginCredentialDAO = new LoginCredentialDAO();
        assertTrue(loginCredentialDAO.register(credential));
    }


    @Test
    public void testSearchLoginCredential() throws Exception {
        System.out.println("searchLoginCredential");
        LoginCredential credential = null;
        LoginCredentialDAO instance = new LoginCredentialDAO();
        LoginCredential expResult = null;
        LoginCredential result = instance.searchLoginCredential(credential);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
