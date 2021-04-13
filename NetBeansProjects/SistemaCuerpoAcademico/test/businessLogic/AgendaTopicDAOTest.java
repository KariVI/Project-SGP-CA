/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

package businessLogic;

import domain.AgendaTopic;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;


public class AgendaTopicDAOTest {
    @Test
    public void testSave() {
        System.out.println("save");
        AgendaTopic agendaTopic = new AgendaTopic("Tema gatrocafe","11:00","12:00","1234");
        int idMeeting = 1;
        AgendaTopicDAO agendaTopicDAO = new AgendaTopicDAO();
            assertTrue(agendaTopicDAO.save(agendaTopic, idMeeting));
    }
    
    @Test
    public void testGetAgendaTopics() {
        System.out.println("getAgendaTopics");
        int idMeeting = 1;
        AgendaTopicDAO agendaTopicDAO = new AgendaTopicDAO();
        ArrayList<AgendaTopic > result = agendaTopicDAO.getAgendaTopics(idMeeting);
        assertNotNull(result);
    }
    
}
 */
