
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
        Topic topic = new Topic("Tema gastrocafe","11:00","12:00","4065161",1);
        TopicDAO topicDAO = new TopicDAO();
        assertTrue(topicDAO.save(topic));
    }
    
    @Test
    public void testGetAgendaTopics()throws BusinessException {
        System.out.println("getAgendaTopics");
        int idMeeting = 1;
        TopicDAO agendaTopicDAO = new TopicDAO();
        ArrayList<Topic > result = agendaTopicDAO.getAgendaTopics(idMeeting);
        assertNotNull(result);
    }
    
    @Test 
    public void testUpdate() throws BusinessException {
        System.out.println("update");
        Topic newTopic = new Topic("Tema gastrocafe","12:00","13:00","4065161",1);
        TopicDAO topicDAO = new TopicDAO();
        assertTrue(topicDAO.update( newTopic));
    }

    @Test 
    public void testDelete() throws BusinessException {
        System.out.println("delete");
        Topic topic = new Topic(1,"Tema gastrocafe","12:00","13:00","4065161",1);
        TopicDAO topicDAO = new TopicDAO();
        assertTrue(topicDAO.delete(topic));
    }
    
}
