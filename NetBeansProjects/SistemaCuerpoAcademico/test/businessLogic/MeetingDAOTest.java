
package businessLogic;

import domain.Meeting;
import domain.Member;
import org.junit.Test;
import static org.junit.Assert.*;

public class MeetingDAOTest {

    @Test
    
    public void testSearchId(){ 
        MeetingDAO meetingDAO= new MeetingDAO();
        Meeting meetingAuxiliar;
        meetingAuxiliar = new Meeting("Revisión de avances en proyectos actuales" ,"04/05/2021","11:30");
        int idExpected=1;
        int idMeeting= meetingDAO.searchId(meetingAuxiliar);
        assertEquals("Prueda de id Meeting", idExpected, idMeeting);
    
    }
    
    @Test
    public void testSearchIdNotFound(){ 
        MeetingDAO meetingDAO= new MeetingDAO();
        Meeting meetingAuxiliar;
        meetingAuxiliar = new Meeting("Lanzamiento FEIBook" ,"11/11/2020","12:30");
        int idExpected=0;
        int idMeeting= meetingDAO.searchId(meetingAuxiliar);
        assertEquals("Prueda de id Meeting", idExpected, idMeeting);
    }
    
    @Test
    public void testFindMeetingById(){  
        MeetingDAO meetingDAO= new MeetingDAO();
        Meeting meetingAuxiliar;
        meetingAuxiliar = new Meeting("Revisión de avances en proyectos actuales" ,"04/05/2021","11:30");
        int idMeeting= meetingDAO.searchId(meetingAuxiliar);
        assertTrue(meetingDAO.findMeetingById(idMeeting));
    }
    
    @Test
    public void testFindMeetingByIdNotFound(){  
        MeetingDAO meetingDAO= new MeetingDAO();
        Meeting meetingAuxiliar;
        meetingAuxiliar = new Meeting("Lanzamiento FEIBook" ,"11/11/2020","12:30");
        int idMeeting= meetingDAO.searchId(meetingAuxiliar);
        assertFalse(meetingDAO.findMeetingById(idMeeting));
    }
    
    
    @Test
    public void testSave(){
        MeetingDAO meetingDAO= new MeetingDAO();
        Meeting meetingAuxiliar;
        meetingAuxiliar = new Meeting("Planeación del próximo seminario" ,"05/06/2021","14:30");   
        assertTrue( meetingDAO.save(meetingAuxiliar));
    }
   
    @Test
    public void testGetMeetingById(){
        MeetingDAO meetingDAO= new MeetingDAO();
        Meeting meetingExpected;
        meetingExpected = new Meeting(1, "Revisión de avances en proyectos actuales" ,"04/05/2021","11:30", "Registrada");
        Meeting meetingResult=meetingDAO.getMeetingById(1);
        
        assertTrue(meetingExpected. equals(meetingResult ));
        
    }
    
    @Test
        public void testGetMeetingByIdNotFound(){
        MeetingDAO meetingDAO= new MeetingDAO();
        Meeting meetingExpected;
        meetingExpected = new Meeting(1, "Revisión de avances en proyectos actuales" ,"04/05/2021","11:30", "Registrada");
        Meeting meetingResult=meetingDAO.getMeetingById(2);
        
        assertFalse(meetingExpected. equals(meetingResult ));
        
    }
        
    @Test    
    public void testgetAssistant(){ 
      MeetingDAO meetingDAO= new MeetingDAO();
      Member memberExpected= new Member("8325134","Juan Carlos Perez Arriaga");
      Member memberResult= meetingDAO.getAssistant(1, "8325134");
      
      assertTrue(memberExpected.equals(memberResult));
    }
    
    @Test
    public void testgetAssistantFailed(){ 
      MeetingDAO meetingDAO= new MeetingDAO();
      Member memberExpected= new Member("7938268","Maria Karen Cortes Verdin");
      Member memberResult= meetingDAO.getAssistant(1,"7938268");
      
      assertFalse(memberExpected.equals(memberResult));
    }
        
    @Test
    public void testAddAssistant(){
        MeetingDAO meetingDAO= new MeetingDAO();
        String enrollment= "7938268";
        int idMeeting=1;
        String role="Lider";
        assertTrue(meetingDAO.addAssistant(idMeeting, enrollment, role));
        
    
    }
    
}


