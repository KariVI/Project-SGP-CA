
package businessLogic;

import domain.PreliminarProject;
import domain.ReceptionWork;
import log.BusinessException;

public interface IReceptionWorkDAO {
    public boolean save(ReceptionWork receptionWork)  throws BusinessException;
    public boolean update(int id, ReceptionWork receptionWork)  throws BusinessException;
}
