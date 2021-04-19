
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
        Topic agendaTopic = new Topic("Tema gastrocafe","11:00","12:00","1234");
        int idMeeting = 1;
        TopicDAO agendaTopicDAO = new TopicDAO();
            assertTrue(agendaTopicDAO.save(agendaTopic, idMeeting));
    }
    
    @Test
    public void testGetAgendaTopics()throws BusinessException {
        System.out.println("getAgendaTopics");
        int idMeeting = 1;
        TopicDAO agendaTopicDAO = new TopicDAO();
        ArrayList<Topic > result = agendaTopicDAO.getAgendaTopics(idMeeting);
        assertNotNull(result);
    }
    
}
