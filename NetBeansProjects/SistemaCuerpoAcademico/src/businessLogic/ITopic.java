
package businessLogic;

import domain.Topic;
import java.util.ArrayList;


public interface ITopic {
      public boolean save(Topic agendaTopic, int idMeeting);
      public ArrayList<Topic>  getAgendaTopics(int idReunion);
}
