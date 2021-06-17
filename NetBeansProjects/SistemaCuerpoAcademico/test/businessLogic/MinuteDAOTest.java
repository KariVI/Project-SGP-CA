package businessLogic;

import domain.Member;
import domain.Minute;
import domain.MinuteComment;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import log.BusinessException;

public class MinuteDAOTest {

    @Test
    public void testSaveMinute() throws BusinessException {
        System.out.println("saveMinute");
        Minute minute = new Minute("Los acuerdos registrados son pendientes","Registrada","Revisar el punto 1.3",3);
        MinuteDAO minuteDAO = new MinuteDAO();
        assertTrue(minuteDAO.savedSucessfulMinute(minute)); 
    }

    @Test
    public void testApproveMinute() throws BusinessException {
        System.out.println("approveMinute");
        int idMinute = 1;
        String professionalLicense = "8325134";
        MinuteDAO minuteDAO = new MinuteDAO();
        minuteDAO.approveMinute(idMinute, professionalLicense);
    }


    @Test
    public void testDisapproveMinute() throws BusinessException{
        System.out.println("disapproveMinute");
        int idMinute = 1;
        String professionalLicense = "7938268";
        MinuteComment minuteComment = new MinuteComment("No se encuentran algunos acuerdos.",professionalLicense,idMinute);
        MinuteDAO minuteDAO = new MinuteDAO();
        minuteDAO.disapproveMinute(minuteComment);
    }
    
    @Test
    public void testGetMinutes() throws BusinessException {
        System.out.println("getMinutes");
        MinuteDAO instanceMinute = new MinuteDAO();
        ArrayList<Minute> result = instanceMinute.getMinutes();
        ArrayList<Minute> resultExpected = new ArrayList<Minute>();
        Minute minute1 = new Minute(3,"Los acuerdos registrados son pendientes","Registrada","Revisar el punto 1.3",3);
        Minute minute2 = new Minute(2,"La segunda version de FEIBook estará disponible hasta el 11 de septiembre","Registrada","Sin pendientes",4);
        Minute minute3 = new Minute(1,"El seminario se realizara el dia 13 de agosto del 2022","Registrada","Asignar horario a ponentes",2);
        resultExpected.add(minute3);
        resultExpected.add(minute2);
        resultExpected.add(minute1);
        assertEquals(result,resultExpected);
    }  
    
    @Test
    public void testGetMinuteComments() throws BusinessException {
        System.out.println("getMinutesComments");
        MinuteDAO instance = new MinuteDAO();
        int idMinute = 2;
        ArrayList<MinuteComment> result = instance.getMinutesComments(idMinute);
        ArrayList<MinuteComment> resultExpected = new ArrayList<MinuteComment>();
        MinuteComment minuteComment = new MinuteComment("Falto el pendiente de agendar la siguiente reunión","Pendiente","8325134",idMinute); 
        resultExpected.add(minuteComment);
        assertEquals(result,resultExpected);
    }

    @Test 
    public void testUpdate() throws BusinessException {
        System.out.println("update");
        Minute minute = new Minute(1,"El seminario se realizara el dia 13 de agosto del 2022","Registrada","Asignar horario a ponentes",2);
        MinuteDAO minuteDAO = new MinuteDAO();
        assertTrue(minuteDAO.updatedSucessful(minute));
    }
    
    @Test
    public void testGetMembersApprove() throws BusinessException {
        System.out.println("approveMinute");
        Minute minute = new Minute(2,"La segunda version de FEIBook estará disponible hasta el 11 de septiembre","Registrada","Sin pendientes",4);
        ArrayList<Member> memberList = new ArrayList<Member>();
        Member member =  new Member("7938268", "Maria Karen Cortes Verdin", "Responsable", "Doctorado", "Ciencias de la Computación", "Centro de Investigación en Matemáticas A.C", 2005, "Activo","JDOEIJ804");
        memberList.add(member);
        MinuteDAO minuteDAO = new MinuteDAO();
        assertEquals(minuteDAO.getMembersApprove(minute),memberList);
    }
    
    @Test
    public void getIdMinute() throws BusinessException {
        System.out.println("get id minute");
        Minute minute = new Minute("Los acuerdos registrados son pendientes","Registrada","Revisar el punto 1.3",3);
        int idMinuteExpected = 3;
        MinuteDAO minuteDAO = new MinuteDAO();
        int idMinuteResut = minuteDAO.getIdMinute(minute);
        assertEquals(idMinuteResut,idMinuteExpected);   
    }
    
    
    @Test (expected = BusinessException.class)
    public void getIdMinuteNotFound() throws BusinessException {
        Minute minute = new Minute(2,"Los acuerdos registrados son pendientes","Registrada","Revisar el punto 1.3",3);
        int idMinuteExpected = 1;
        MinuteDAO minuteDAO = new MinuteDAO();
        int idMinuteResut = minuteDAO.getIdMinute(minute);
        assertEquals(idMinuteResut,idMinuteExpected);
    }
    
    @Test
    public void testDeleteComments() throws BusinessException{
        int idMinute = 1;
        MinuteDAO minuteDAO = new MinuteDAO();
        assertTrue(minuteDAO.deletedSucessfulMinutesComments(idMinute));   
    }
    
    @Test
    public void testDeleteComment() throws BusinessException{
        int idMinute = 1;
        String professionalLicense = "7938268";
        MinuteComment minuteComment = new MinuteComment("No se encuentran algunos acuerdos.",professionalLicense,idMinute);
        MinuteDAO minuteDAO = new MinuteDAO();
        assertTrue(minuteDAO.deletedSucessfulMinuteComment(minuteComment));   
    }
    
    @Test
    public void testGetMinute() throws BusinessException{
        Minute minuteExpected = new Minute(2,"La segunda version de FEIBook estará disponible hasta el 11 de septiembre","Registrada","Sin pendientes",4);
        int idMeeting = 4;
        MinuteDAO minuteDAO = new MinuteDAO();
        assertEquals(minuteDAO.getMinute(idMeeting),minuteExpected);
    }
}
