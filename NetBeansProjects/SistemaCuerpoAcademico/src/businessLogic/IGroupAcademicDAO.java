
package businessLogic;

import domain.GroupAcademic;
import domain.LGAC;
<<<<<<< HEAD
import java.util.ArrayList;
=======
>>>>>>> MarianaChangesGUI
import log.BusinessException;

public interface IGroupAcademicDAO {
    public boolean savedSucessful(GroupAcademic groupAcademic) throws BusinessException;
    public GroupAcademic getGroupAcademicById(String key)throws BusinessException;
<<<<<<< HEAD
    public boolean addedLGACSucessful(GroupAcademic groupAcademic, LGAC lgac) throws BusinessException;
    public boolean updatedSucessful(String lastKey,GroupAcademic groupAcademic) throws BusinessException;
    public ArrayList<LGAC> getLGACs(String keyGroupAcademic) throws BusinessException;
    public boolean deletedLGACSuccesful(String keyGroupAcademic, LGAC lgac) throws BusinessException;
=======
    public boolean addLGAC(GroupAcademic groupAcademic, LGAC lgac) throws BusinessException;
    public boolean update(String lastKey,GroupAcademic groupAcademic) throws BusinessException;
>>>>>>> MarianaChangesGUI
}
