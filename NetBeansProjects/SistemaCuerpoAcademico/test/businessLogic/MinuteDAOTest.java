/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import domain.Minute;
import domain.MinuteComment;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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


    @Test
    public void testSaveMinute() {
        System.out.println("saveMinute");
        Minute minute = new Minute("Los acuerdos registrados son pendiente","Estado","Falta gastrocafe");
        int idMeeting = 1;
        MinuteDAO minuteDAO = new MinuteDAO();
        assertTrue(minuteDAO.saveMinute(minute, idMeeting)); 
    }

    @Test
    public void testApproveMinute() {
        System.out.println("approveMinute");
        int idMinute = 1;
        String professionalLicense = "1234";
        MinuteDAO minuteDAO = new MinuteDAO();
        try{
            minuteDAO.approveMinute(idMinute, professionalLicense);
        }
        catch (IllegalStateException ex) {
            Logger.getLogger(ProjectDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }


    @Test
    public void testDisapproveMinute() {
        System.out.println("disapproveMinute");
        int minute = 1;
        String professionalLicense = "1234";
        String comments = "No se encuentran algunos acuerdos;";
        MinuteDAO minuteDAO = new MinuteDAO();
        try{
            minuteDAO.disapproveMinute(minute, professionalLicense, comments);
        }
        catch (IllegalStateException ex) {
            Logger.getLogger(ProjectDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
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
