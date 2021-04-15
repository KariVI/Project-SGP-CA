
package businessLogic;

import domain.GroupAcademic;
import log.BusinessException;

public interface IGroupAcademicDAO {
    public boolean save(GroupAcademic groupAcademic) throws BusinessException;
    public GroupAcademic getGroupAcademicById(String key)throws BusinessException;
}
