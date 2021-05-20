
package businessLogic;

import domain.Topic;
import java.util.ArrayList;
import log.BusinessException;

public interface ITopic {
      public boolean save(Topic agendaTopic) throws BusinessException;
      public ArrayList<Topic>  getAgendaTopics(int idReunion) throws BusinessException;
      public boolean update(Topic newTopic) throws BusinessException;
      public boolean delete(Topic topic) throws BusinessException;
}
