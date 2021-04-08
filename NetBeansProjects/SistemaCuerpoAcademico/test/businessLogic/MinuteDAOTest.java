/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import domain.Minute;
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
    
}
