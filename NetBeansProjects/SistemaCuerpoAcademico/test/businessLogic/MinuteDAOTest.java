/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import domain.Minute;
import domain.MinuteComment;
import java.util.ArrayList;
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
public class MinuteDAOTest {
    
    public MinuteDAOTest() {
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
     * Test of saveMinute method, of class MinuteDAO.
     */
    @Test
    public void testSaveMinute() {
        System.out.println("saveMinute");
        Minute minute = new Minute("Esta es una nota","Estado","Esto es un pendiente");
        int idMeeting = 1;
        MinuteDAO instance = new MinuteDAO();
        instance.saveMinute(minute, idMeeting);
        // TODO review the generated test code and remove the default call to fail.
       
    }
    
    /**
     * Test of approveMinute method, of class MinuteDAO.
     */
    @Test
    public void testApproveMinute() {
        System.out.println("approveMinute");
        int idMinute = 1;
        String professionalLicense = "1234";
        MinuteDAO instance = new MinuteDAO();
        instance.approveMinute(idMinute, professionalLicense);
    }

    /**
     * Test of disapproveMinute method, of class MinuteDAO.
     */
    @Test
    public void testDisapproveMinute() {
        System.out.println("disapproveMinute");
        int minute = 1;
        String professionalLicense = "1234";
        String comments = "No se encuentran algunos acuerdos;";
        MinuteDAO instance = new MinuteDAO();
        instance.disapproveMinute(minute, professionalLicense, comments);
    }
    
    @Test
    public void testGetMinutes() {
        System.out.println("getMinutes");
        MinuteDAO instanceMinute = new MinuteDAO();
        ArrayList<Minute> result = instanceMinute.getMinutes();
        assertNotNull(result);
    }  
    
    @Test
    public void testGetMinutesComments() {
        System.out.println("getMinutesComments");
        int idMinute = 1;
        MinuteDAO instance = new MinuteDAO();
        ArrayList<MinuteComment> result = instance.getMinutesComments(idMinute);
        assertNotNull(result);
    }
}
