/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import GUI.Validation;
import domain.LoginCredential;
import java.math.BigInteger;
import java.security.MessageDigest;
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
        LoginCredential credential = new LoginCredential("JuanPer","12345","8325134");
        LoginCredentialDAO loginCredentialDAO = new LoginCredentialDAO();
        assertTrue(loginCredentialDAO.registerSuccesful(credential));
    }
 

    @Test
    public void testSearchLoginCredential() throws Exception {
        LoginCredential credential = new LoginCredential("JuanPer","12345","8325134");
        LoginCredentialDAO loginCredentialDAO = new LoginCredentialDAO();
        System.out.println(loginCredentialDAO.searchLoginCredential(credential).getPassword());
        assertEquals("12345",loginCredentialDAO.searchLoginCredential(credential).getPassword());
    }
    
}
