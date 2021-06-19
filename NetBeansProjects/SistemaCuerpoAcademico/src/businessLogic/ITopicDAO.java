
package businessLogic;

import domain.Topic;
import java.util.ArrayList;
import log.BusinessException;

public interface ITopicDAO {
      public boolean savedSucessful(Topic agendaTopic) throws BusinessException;
      public ArrayList<Topic>  getAgendaTopics(int idReunion) throws BusinessException;
      public boolean updatedSucessful(Topic newTopic) throws BusinessException;
      public boolean deletedSucessful(Topic topic) throws BusinessException;
}
