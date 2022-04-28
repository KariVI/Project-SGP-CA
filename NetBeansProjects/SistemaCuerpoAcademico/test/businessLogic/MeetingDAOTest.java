
package businessLogic;

import domain.Meeting;
import domain.Member;
import java.util.ArrayList;
import log.BusinessException;
import log.Log;
import org.junit.Test;
import static org.junit.Assert.*;

public class MeetingDAOTest {

    @Test
    
    public void testGetId() throws BusinessException{ 
        MeetingDAO meetingDAO= new MeetingDAO();
        Meeting meetingAuxiliar;
        meetingAuxiliar = new Meeting("Revisión de avances en proyectos actuales" ,"04/08/2021","11:30","JDOEIJ804");
        int idExpected=1;
        int idMeeting= meetingDAO.getId(meetingAuxiliar);
        assertEquals("Prueda de id Meeting", idExpected, idMeeting);
    
    }
    
    @Test
    public void testGetIdNotFound(){ 
        MeetingDAO meetingDAO= new MeetingDAO();
        Meeting meetingAuxiliar;
        boolean value=true;
        meetingAuxiliar = new Meeting("Lanzamiento Gastrocafe" ,"11/11/2020","12:30","JDOEIJ804");
        int idExpected=8;
        try{
            int idMeeting= meetingDAO.getId(meetingAuxiliar);
        }catch(BusinessException ex){
            value=false;
        }
      assertFalse(value);

    }
    
 
    
    @Test
    public void testSave() throws BusinessException{
        MeetingDAO meetingDAO= new MeetingDAO();
        Meeting meetingAuxiliar;
        meetingAuxiliar = new Meeting("Planeación del próximo seminario" ,"05/06/2021","14:30","JDOEIJ804");   
        assertTrue( meetingDAO.savedSucessful(meetingAuxiliar));
    }
   
    @Test
    public void testGetMeetingById() throws BusinessException{
        MeetingDAO meetingDAO= new MeetingDAO();
        Meeting meetingExpected;
        meetingExpected = new Meeting(1, "Revisión de avances en proyectos actuales" ,"04/08/2021","11:30", "Registrada");
        Meeting meetingResult=meetingDAO.getMeetingById(1);
        
        assertTrue(meetingExpected. equals(meetingResult ));
        
    }
    
    @Test
        public void testGetMeetingByIdNotFound() throws BusinessException{
        MeetingDAO meetingDAO= new MeetingDAO();
        Meeting meetingExpected;
        boolean value=true;
        meetingExpected = new Meeting(1, "Revisión de avances en proyectos actuales" ,"04/05/2021","11:30", "Registrada");
        Meeting meetingResult=null;
        try{
            meetingResult=meetingDAO.getMeetingById(12);
        } catch(BusinessException ex){
            value=false;
        }
      assertFalse(value);
        
    }
   
    @Test
    public void testUpdate() throws BusinessException{  
        MeetingDAO meetingDAO= new MeetingDAO();
        Meeting meeting;
        meeting= new Meeting( "Organizar agenda de actividades del seminario" ,"18/05/2021","15:30","JDOEIJ804"); 
        meeting.setKey(2);
        assertTrue(meetingDAO.updatedSucessful(meeting));
    }
    
    @Test
    public void testGetMeetings()throws BusinessException{
        ArrayList<Meeting> arrayMeetings;
        MeetingDAO meetingDAO=new MeetingDAO();
        arrayMeetings=meetingDAO.getMeetings("JDOEIJ804", "7938268");
        int idExpected=2;
        int result=arrayMeetings.get(0).getKey();
        assertEquals(idExpected, result);
    }
    
    @Test
    public void testChangeState() throws BusinessException{
        Meeting meeting= new Meeting(3,"Actualizar plan de trabajo de la LIS","13:00","11/08/2021", "Registrada");
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
      Meeting meeting= new Meeting(2, "Organizar agenda de actividades del seminario" ,"15/09/2021","15:30","Concluida");  
      String professionalLicenseExpected= "7938268";
      meeting.setAssistants(meetingDAO.getAssistants(2));
      String professionalLicenseResult=meeting.getAssistants().get(0).getProfessionalLicense();
      assertEquals(professionalLicenseExpected, professionalLicenseResult);
    }
    
    @Test     
    public void testDeleteAssistants() throws BusinessException{  
        MeetingDAO meetingDAO= new MeetingDAO();
        Meeting meeting= new Meeting(4, "Lanzamiento FEIBook" ,"11/11/2021","12:30","Registrada");
        Member assistant= new Member("7938268","Maria Karen Cortes Verdin","Lider");
        meeting.addAssistant(assistant);
        assertTrue(meetingDAO.deletedSucessfulAssistants(meeting,assistant));
    }
}


