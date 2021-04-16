
package businessLogic;

import domain.Member;
import domain.Prerequisite;
import log.BusinessException;

public interface IPrerequisiteDAO {
        public boolean savePrerequisites(Prerequisite prerequisite, int idMeeting) throws BusinessException;
        public String getPrerequisiteDescription(int id, int idMeeting) throws BusinessException;
        public int getId(Prerequisite prerequisite, int idMeeting) throws BusinessException;
        public boolean updatePrerequisite(int id, Prerequisite prerequisite) throws BusinessException;
        public boolean delete(int idPrerequisite)throws BusinessException;



    
}
