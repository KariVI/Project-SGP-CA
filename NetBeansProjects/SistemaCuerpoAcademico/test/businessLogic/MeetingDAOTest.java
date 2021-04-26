
package businessLogic;

import domain.Meeting;
import domain.Member;
import java.util.ArrayList;
import log.BusinessException;
import org.junit.Test;
import static org.junit.Assert.*;

public class MeetingDAOTest {

    @Test
    
    public void testSearchId() throws BusinessException{ 
        MeetingDAO meetingDAO= new MeetingDAO();
        Meeting meetingAuxiliar;
        meetingAuxiliar = new Meeting("Revisión de avances en proyectos actuales" ,"04/05/2021","11:30");
        int idExpected=1;
        int idMeeting= meetingDAO.searchId(meetingAuxiliar);
        assertEquals("Prueda de id Meeting", idExpected, idMeeting);
    
    }
    
    @Test
    public void testSearchIdNotFound() throws BusinessException{ 
        MeetingDAO meetingDAO= new MeetingDAO();
        Meeting meetingAuxiliar;
        meetingAuxiliar = new Meeting("Lanzamiento FEIBook" ,"11/11/2020","12:30");
        int idExpected=0;
        int idMeeting= meetingDAO.searchId(meetingAuxiliar);
        assertEquals("Prueda de id Meeting", idExpected, idMeeting);
    }
    
    @Test
    public void testFindMeetingById() throws BusinessException{  
        MeetingDAO meetingDAO= new MeetingDAO();
        Meeting meetingAuxiliar;
        meetingAuxiliar = new Meeting("Revisión de avances en proyectos actuales" ,"04/05/2021","11:30");
        int idMeeting= meetingDAO.searchId(meetingAuxiliar);
        assertTrue(meetingDAO.findMeetingById(idMeeting));
    }
    
    @Test
    public void testFindMeetingByIdNotFound() throws BusinessException{  
        MeetingDAO meetingDAO= new MeetingDAO();
        Meeting meetingAuxiliar;
        meetingAuxiliar = new Meeting("Lanzamiento FEIBook" ,"11/11/2020","12:30");
        int idMeeting= meetingDAO.searchId(meetingAuxiliar);
        assertFalse(meetingDAO.findMeetingById(idMeeting));
    }
    
    
    @Test
    public void testSave() throws BusinessException{
        MeetingDAO meetingDAO= new MeetingDAO();
        Meeting meetingAuxiliar;
        meetingAuxiliar = new Meeting("Planeación del próximo seminario" ,"05/06/2021","14:30");   
        assertTrue( meetingDAO.save(meetingAuxiliar));
    }
   
    @Test
    public void testGetMeetingById() throws BusinessException{
        MeetingDAO meetingDAO= new MeetingDAO();
        Meeting meetingExpected;
        meetingExpected = new Meeting(1, "Revisión de avances en proyectos actuales" ,"04/05/2021","11:30", "Registrada");
        Meeting meetingResult=meetingDAO.getMeetingById(1);
        
        assertTrue(meetingExpected. equals(meetingResult ));
        
    }
    
    @Test
        public void testGetMeetingByIdNotFound() throws BusinessException{
        MeetingDAO meetingDAO= new MeetingDAO();
        Meeting meetingExpected;
        meetingExpected = new Meeting(1, "Revisión de avances en proyectos actuales" ,"04/05/2021","11:30", "Registrada");
        Meeting meetingResult=meetingDAO.getMeetingById(2);
        
        assertFalse(meetingExpected. equals(meetingResult ));
        
    }
        
    @Test    
    public void testgetAssistant() throws BusinessException{ 
      MeetingDAO meetingDAO= new MeetingDAO();
      Member memberExpected= new Member("8325134","Juan Carlos Perez Arriaga");
      Member memberResult= meetingDAO.getAssistant(1, "8325134");
      
      assertTrue(memberExpected.equals(memberResult));
    }
    
    @Test
    public void testgetAssistantFailed() throws BusinessException{ 
      MeetingDAO meetingDAO= new MeetingDAO();
      Member memberExpected= new Member("7938268","Maria Karen Cortes Verdin");
      Member memberResult= meetingDAO.getAssistant(1,"7938268");
      
      assertFalse(memberExpected.equals(memberResult));
    }
        
    @Test
    public void testAddAssistant() throws BusinessException{
        MeetingDAO meetingDAO= new MeetingDAO();
        String enrollment= "7938268";
        int idMeeting=1;
        String role="Lider";
        assertTrue(meetingDAO.addAssistant(idMeeting, enrollment, role));
        
    
    }
    
    @Test
    public void testUpdate() throws BusinessException{  
        MeetingDAO meetingDAO= new MeetingDAO();
        Meeting meeting;
        meeting= new Meeting( "Organizar agenda de actividades del seminario" ,"15 /05/2021","15:30"); 
        meeting.setKey(3);
        assertTrue(meetingDAO.update(meeting));
    }
    
    @Test
    public void testGetMeetings()throws BusinessException{
        ArrayList<Meeting> arrayMeetings;
        MeetingDAO meetingDAO=new MeetingDAO();
        arrayMeetings=meetingDAO.getMeetings();
        int idExpected=1;
        int result=arrayMeetings.get(0).getKey();
        assertEquals(idExpected, result);
    }
    
    @Test
    public void testChangeState() throws BusinessException{
        Meeting meeting= new Meeting(4,"Actualizar plan de trabajo de la LIS","13:00","11/05/2021", "Proxima");
        MeetingDAO meetingDAO= new MeetingDAO();
        assertTrue(meetingDAO.changeState(meeting));
    }
}


