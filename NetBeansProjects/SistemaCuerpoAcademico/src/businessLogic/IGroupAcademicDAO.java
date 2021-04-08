
package businessLogic;

import domain.GroupAcademic;

public interface IGroupAcademicDAO {
    public void save(GroupAcademic groupAcademic);
    public GroupAcademic getGroupAcademicById(String key);
}
