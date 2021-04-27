
package businessLogic;

import domain.GroupAcademic;
import domain.LGCA;
import log.BusinessException;

public interface IGroupAcademicDAO {
    public boolean save(GroupAcademic groupAcademic) throws BusinessException;
    public GroupAcademic getGroupAcademicById(String key)throws BusinessException;
    public boolean addLGAC(GroupAcademic groupAcademic, LGCA lgac) throws BusinessException;
    public boolean update(String lastKey,GroupAcademic groupAcademic) throws BusinessException;
}
