
package businessLogic;

import domain.Topic;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;


public class TopicDAOTest {
    @Test
    public void testSave() {
        System.out.println("save");
        Topic agendaTopic = new Topic("Tema gatrocafe","11:00","12:00","1234");
        int idMeeting = 1;
        TopicDAO agendaTopicDAO = new TopicDAO();
            assertTrue(agendaTopicDAO.save(agendaTopic, idMeeting));
    }
    
    @Test
    public void testGetAgendaTopics() {
        System.out.println("getAgendaTopics");
        int idMeeting = 1;
        TopicDAO agendaTopicDAO = new TopicDAO();
        ArrayList<Topic > result = agendaTopicDAO.getAgendaTopics(idMeeting);
        assertNotNull(result);
    }
    
}
