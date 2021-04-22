
package businessLogic;

import domain.GroupAcademic;
import domain.LGAC;
import log.BusinessException;

public interface IGroupAcademicDAO {
    public boolean save(GroupAcademic groupAcademic) throws BusinessException;
    public GroupAcademic getGroupAcademicById(String key)throws BusinessException;
    public boolean addLGAC(GroupAcademic groupAcademic, LGAC lgac) throws BusinessException;
    public boolean update(String lastKey,GroupAcademic groupAcademic) throws BusinessException;
}
