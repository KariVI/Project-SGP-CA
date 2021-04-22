
package businessLogic;

import domain.ReceptionWork;
import log.BusinessException;

public interface IReceptionWorkDAO {
    public boolean save(ReceptionWork receptionWork)  throws BusinessException;
    public boolean update(int id, ReceptionWork receptionWork)  throws BusinessException;
    public ReceptionWork getReceptionWorkById(int id) throws BusinessException;
}
