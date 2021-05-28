/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import domain.Member;
import domain.Minute;
import domain.MinuteComment;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import log.BusinessException;
/**
 *
 * @author Mariana
 */
public class MinuteDAOTest {

    @Test
    public void testSaveMinute() throws BusinessException {
        System.out.println("saveMinute");
        Minute minute = new Minute("Los acuerdos registrados son pendiente","Estado","Falta gastrocafe",1);
        MinuteDAO minuteDAO = new MinuteDAO();
        assertTrue(minuteDAO.saveMinute(minute)); 
    }

    @Test
    public void testApproveMinute() throws BusinessException {
        System.out.println("approveMinute");
        int idMinute = 1;
        String professionalLicense = "4065161";
        MinuteDAO minuteDAO = new MinuteDAO();
        try{
            minuteDAO.approveMinute(idMinute, professionalLicense);
        }
        catch (IllegalStateException ex) {
            Logger.getLogger(ProjectDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }


    @Test
    public void testDisapproveMinute() throws BusinessException{
        System.out.println("disapproveMinute");
        int idMinute = 1;
        String professionalLicense = "8325134";
        String comments = "No se encuentran algunos acuerdos;";
        MinuteComment minuteComment = new MinuteComment("No se encuentran algunos acuerdos;",professionalLicense,idMinute);
        MinuteDAO minuteDAO = new MinuteDAO();
        try{
            minuteDAO.disapproveMinute(minuteComment);
        }
        catch (IllegalStateException ex) {
            Logger.getLogger(ProjectDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testGetMinutes() throws BusinessException {
        System.out.println("getMinutes");
        MinuteDAO instanceMinute = new MinuteDAO();
        ArrayList<Minute> result = instanceMinute.getMinutes();
        ArrayList<Minute> resultExpected = new ArrayList<Minute>();
        int idMinute = 1;
        int idMeeting = 1;
        Minute minute = new Minute(idMinute,"Los acuerdos registrados son pendiente","Completado","Falta gastrocafe",idMeeting);
        resultExpected.add(minute);
        assertEquals(result,resultExpected);
    }  
    
    @Test
    public void testGetMinutesComments() throws BusinessException {
        System.out.println("getMinutesComments");
        MinuteDAO instance = new MinuteDAO();
        int idMinute = 1;
        ArrayList<MinuteComment> result = instance.getMinutesComments(idMinute);
        ArrayList<MinuteComment> resultExpected = new ArrayList<MinuteComment>();
        MinuteComment minuteComment = new MinuteComment("No se encuentran algunos acuerdos;","Pendiente","8325134",idMinute);
        resultExpected.add(minuteComment);
        assertEquals(result,resultExpected);
    }

    @Test 
    public void testUpdate() throws BusinessException {
        System.out.println("update");
        Minute newMinute = new Minute(1,"Los acuerdos registrados son pendiente","Completado","Falta gastrocafe",1);
        MinuteDAO minuteDAO = new MinuteDAO();
        assertTrue(minuteDAO.update(newMinute));
    }
    
    @Test
    public void testGetMembersApprove() throws BusinessException {
        System.out.println("approveMinute");
         Minute minute = new Minute(1,"Los acuerdos registrados son pendiente","Estado","Falta gastrocafe",1);
         ArrayList<Member> memberList = new ArrayList<Member>();
         Member member = new Member("4065161", "Maria de los Angeles Arenas Valdes","Colaborador","Maestria","Maestria en ciencias de la computacion","Fundacion Arturo Rosenbulth",1999,"1491");
         memberList.add(member);
        MinuteDAO minuteDAO = new MinuteDAO();
        assertEquals(minuteDAO.getMembersApprove(minute),memberList);
    }
    
    @Test
    public void getIdMinute() throws BusinessException {
        System.out.println("get id minute");
        Minute minute = new Minute("Los acuerdos registrados son pendiente","Completado","Falta gastrocafe",1);
        int idMinuteExpected = 1;
        MinuteDAO minuteDAO = new MinuteDAO();
        int idMinuteResut = minuteDAO.getIdMinute(minute);
        assertEquals(idMinuteResut,idMinuteExpected);   
    }
    
    
    @Test (expected = BusinessException.class)
    public void getIdMinuteNotFound() throws BusinessException {
        System.out.println("get id minute");
        Minute minute = new Minute("Los acuerdos registrados son pendiente","Estado","Falta gastrocafe",1);
        int idMinuteExpected = 1;
        MinuteDAO minuteDAO = new MinuteDAO();
        int idMinuteResut = minuteDAO.getIdMinute(minute);
        assertEquals(idMinuteResut,idMinuteExpected);
    }
}
