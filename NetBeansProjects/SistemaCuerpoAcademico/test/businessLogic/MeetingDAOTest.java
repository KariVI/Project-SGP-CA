
package businessLogic;

import domain.Meeting;
import domain.Member;
import java.util.ArrayList;
import log.BusinessException;
import org.junit.Test;
import static org.junit.Assert.*;

public class MeetingDAOTest {

    @Test
    
    public void testGetId() throws BusinessException{ 
        MeetingDAO meetingDAO= new MeetingDAO();
        Meeting meetingAuxiliar;
        meetingAuxiliar = new Meeting("Revisión de avances en proyectos actuales" ,"04/05/2021","11:30");
        int idExpected=1;
        int idMeeting= meetingDAO.getId(meetingAuxiliar);
        assertEquals("Prueda de id Meeting", idExpected, idMeeting);
    
    }
    
    @Test
    public void testGetIdNotFound() throws BusinessException{ 
        MeetingDAO meetingDAO= new MeetingDAO();
        Meeting meetingAuxiliar;
        meetingAuxiliar = new Meeting("Lanzamiento FEIBook" ,"11/11/2020","12:30");
        int idExpected=0;
        int idMeeting= meetingDAO.getId(meetingAuxiliar);
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
        assertTrue( meetingDAO.savedSucessful(meetingAuxiliar));
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
    public void testUpdate() throws BusinessException{  
        MeetingDAO meetingDAO= new MeetingDAO();
        Meeting meeting;
        meeting= new Meeting( "Organizar agenda de actividades del seminario" ,"15 /05/2021","15:30"); 
        meeting.setKey(3);
        assertTrue(meetingDAO.updatedSucessful(meeting));
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
        assertTrue(meetingDAO.changedStateSucessful(meeting));
    }
    
    @Test
    public void testAddAssistants() throws BusinessException{
        MeetingDAO meetingDAO= new MeetingDAO();
        Meeting meeting= new Meeting(1, "Revisión de avances en proyectos actuales" ,"04/05/2021","11:30", "Registrada");
        Member assistant= new Member("7938268","Maria Karen Cortes Verdin","Lider");
        meeting.addAssistant(assistant);
        assertTrue(meetingDAO.addedSucessfulAssistants(meeting));
    }
    
    @Test
    public void testGetAssistants() throws BusinessException{   
      MeetingDAO meetingDAO= new MeetingDAO();
      Meeting meeting= new Meeting(1, "Revisión de avances en proyectos actuales" ,"04/05/2021","11:30", "Registrada");  
      String professionalLicenseExpected= "8325134";
      meeting.setAssistants(meetingDAO.getAssistants(1));
      String professionalLicenseResult=meeting.getAssistants().get(0).getProfessionalLicense();
      assertEquals(professionalLicenseExpected, professionalLicenseResult);
    }
    
    @Test     
    public void testDeleteAssistants() throws BusinessException{  
        MeetingDAO meetingDAO= new MeetingDAO();
        Meeting meeting= new Meeting(1, "Revisión de avances en proyectos actuales" ,"04/05/2021","11:30", "Registrada");
        Member assistant= new Member("7938268","Maria Karen Cortes Verdin","Lider");
        meeting.addAssistant(assistant);
        assertTrue(meetingDAO.deletedSucessfulAssistants(meeting));
    }
}


