
package businessLogic;

import domain.Topic;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import log.BusinessException;

public class TopicDAOTest {
    @Test
    public void testSave() throws BusinessException{
        System.out.println("save");
        Topic topic = new Topic("Tema gastrocafe","11:00","12:00","Maria de los Angeles Arenas Valdez", "4065161",1);
        TopicDAO topicDAO = new TopicDAO();
        assertTrue(topicDAO.savedSucessful(topic));
    }
    
    @Test
    public void testGetAgendaTopics()throws BusinessException {
        System.out.println("getAgendaTopics");
        int idMeeting = 2;
        Topic topic = new Topic("Agenda","11:00","14:00","Maria de los Angeles Arenas Valdez","8325134",2);
        ArrayList<Topic> resultExpected = new ArrayList<Topic>();
        resultExpected.add(topic);
        TopicDAO agendaTopicDAO = new TopicDAO();
        ArrayList<Topic > result = agendaTopicDAO.getAgendaTopics(idMeeting);   
        assertEquals(result,resultExpected);
    }
    
    @Test 
    public void testUpdate() throws BusinessException {
        System.out.println("update");
        Topic newTopic = new Topic("Tema gastrocafe","12:00","13:00","Maria de los Angeles Arenas Valdez","4065161",1);
        TopicDAO topicDAO = new TopicDAO();
        assertTrue(topicDAO.updatedSucessful( newTopic));
    }

    @Test 
    public void testDelete() throws BusinessException {
        System.out.println("delete");
        Topic topic = new Topic(2,"Tema gastrocafe","12:00","13:00","Maria de los Angeles Arenas Valdez","4065161",1);
        TopicDAO topicDAO = new TopicDAO();
        assertTrue(topicDAO.deletedSucessful(topic));
    }
    
}
