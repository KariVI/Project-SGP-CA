
package businessLogic;

import domain.GroupAcademic;
import domain.LGAC;
import java.util.ArrayList;
import log.BusinessException;

public interface IGroupAcademicDAO {
    public boolean savedSucessful(GroupAcademic groupAcademic) throws BusinessException;
    public GroupAcademic getGroupAcademicById(String key)throws BusinessException;
    public boolean addedLGACSucessful(GroupAcademic groupAcademic, LGAC lgac) throws BusinessException;
    public boolean updatedSucessful(String lastKey,GroupAcademic groupAcademic) throws BusinessException;
    public ArrayList<LGAC> getLGACs(String keyGroupAcademic) throws BusinessException;
   
}
