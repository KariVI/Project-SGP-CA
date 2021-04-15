
package businessLogic;

import domain.PreliminarProject;
import log.BusinessException;

public interface IPreliminarProjectDAO {
    public boolean save(PreliminarProject preliminarProject) throws BusinessException;
    public int getId(PreliminarProject preliminarProject)throws BusinessException;
}
