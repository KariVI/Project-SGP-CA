
package businessLogic;

import domain.Topic;
import java.util.ArrayList;
import log.BusinessException;

public interface ITopic {
      public boolean save(Topic agendaTopic, int idMeeting) throws BusinessException;
      public ArrayList<Topic>  getAgendaTopics(int idReunion) throws BusinessException;
}
